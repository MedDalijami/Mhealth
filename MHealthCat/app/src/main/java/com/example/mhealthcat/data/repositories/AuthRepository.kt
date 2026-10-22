package com.example.mhealthcat.data.repositories

import com.example.mhealthcat.models.UserProfile
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

// Single place that talks to Firebase Authentication and the users/{uid} profile document
object AuthRepository {
    private val auth get() = FirebaseAuth.getInstance()
    private val firestore get() = FirebaseFirestore.getInstance()

    // Currently logged-in user, null when logged out. Updates on login, logout and account deletion.
    private val _currentUser = MutableStateFlow(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    init {
        auth.addAuthStateListener { _currentUser.value = it.currentUser }
    }

    val isLoggedIn: Boolean
        get() = auth.currentUser != null

    private fun userDocument(uid: String) = firestore.collection("users").document(uid)

    suspend fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun signUp(
        name: String,
        lastName: String,
        email: String,
        password: String,
        profilePicture: String? = null
    ) {
        val user = auth.createUserWithEmailAndPassword(email, password).await().user
            ?: throw IllegalStateException("Registracija ni uspela")

        val profile = mutableMapOf<String, Any>(
            "name" to name,
            "lastName" to lastName,
            "email" to email,
            "createdAt" to System.currentTimeMillis()
        )
        if (profilePicture != null) {
            profile["profilePicture"] = profilePicture
        }
        userDocument(user.uid).set(profile).await()
    }

    fun logOut() {
        auth.signOut()
    }

    // Live profile of the given user; emits again whenever the document changes
    fun profile(user: FirebaseUser): Flow<UserProfile> = callbackFlow {
        val registration = userDocument(user.uid).addSnapshotListener { document, _ ->
            trySend(
                UserProfile(
                    id = user.uid,
                    email = document?.getString("email") ?: user.email.orEmpty(),
                    name = document?.getString("name").orEmpty(),
                    lastName = document?.getString("lastName").orEmpty(),
                    createdAt = document?.getLong("createdAt") ?: 0L,
                    profilePictureData = document?.getString("profilePicture")
                )
            )
        }
        awaitClose { registration.remove() }
    }

    suspend fun updateProfile(name: String, lastName: String) {
        val user = auth.currentUser ?: return
        userDocument(user.uid)
            .set(mapOf("name" to name, "lastName" to lastName), SetOptions.merge())
            .await()
    }

    // Saves a new thumbnail, or removes the field when picture is null
    suspend fun updateProfilePicture(picture: String?) {
        val user = auth.currentUser ?: return
        userDocument(user.uid)
            .set(mapOf("profilePicture" to (picture ?: FieldValue.delete())), SetOptions.merge())
            .await()
    }

    suspend fun changePassword(currentPassword: String, newPassword: String) {
        val user = reauthenticate(currentPassword)
        user.updatePassword(newPassword).await()
    }

    suspend fun deleteAccount(password: String) {
        val user = reauthenticate(password)

        for (collectionName in EntryCollection.all) {
            val entries = userDocument(user.uid).collection(collectionName).get().await().documents
            entries.chunked(100).forEach { chunk ->
                val batch = firestore.batch()
                chunk.forEach { batch.delete(it.reference) }
                batch.commit().await()
            }
        }

        userDocument(user.uid).delete().await()
        user.delete().await()
    }
    private suspend fun reauthenticate(password: String): FirebaseUser {
        val user = auth.currentUser ?: throw IllegalStateException("Uporabnik ni prijavljen")
        val credential = EmailAuthProvider.getCredential(user.email.orEmpty(), password)
        user.reauthenticate(credential).await()
        return user
    }

    fun errorMessage(
        e: Exception,
        wrongCredentialsText: String = "Vnesen e-mail ali geslo nista pravilna."
    ): String = when (e) {
        is FirebaseAuthUserCollisionException -> "E-poštni naslov je že registriran."
        is FirebaseAuthInvalidUserException,
        is FirebaseAuthInvalidCredentialsException -> wrongCredentialsText
        is FirebaseNetworkException -> "Ni internetne povezave. Poskusite znova."
        else -> "Prišlo je do napake. Poskusite znova."
    }
}
