package com.example.mhealthcat.data.repositories

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
object ServerTimeStamp
{
    fun now(): Long {
        return System.currentTimeMillis()
    }
}

object EntryCollection {
    const val SLEEP = "sleep"
    const val SPORT = "sport"
    const val SOCIAL = "social"
    const val WELLBEING = "wellbeing"

    val all = listOf(SLEEP, SPORT, SOCIAL, WELLBEING)
}

abstract class DataRepository <T : Entry> (
    private val collectionName: String,
    private val itemClass: Class<T>
)
{
    private val firestore get() = FirebaseFirestore.getInstance()

    // Repo runs on whole app so it needs its own scope, not tied to any view model
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @OptIn(ExperimentalCoroutinesApi::class)
    val items: StateFlow<List<T>> = AuthRepository.currentUser
        .flatMapLatest { user ->
            if (user == null) flowOf(emptyList()) else entries(user.uid)
        }
        .stateIn(scope, SharingStarted.Eagerly, emptyList())

    private fun collection(uid: String) : CollectionReference = firestore.collection("users").document(uid).collection(collectionName)
    private fun entries(uid: String): Flow<List<T>> = callbackFlow {
        val registration = collection(uid)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.w(TAG, "Branje vnosov ($collectionName) ni uspelo", error)
                    return@addSnapshotListener
                }
                val list = snapshot?.documents.orEmpty().mapNotNull { document ->
                    document.toObject(itemClass)?.let { stampMetadata(it, document.id, it.timestamp) }
                }
                trySend(list)
            }
        awaitClose { registration.remove() }
    }

    fun add(draft: T) {
        val uid = AuthRepository.currentUser.value?.uid ?: return
        val document = collection(uid).document()
        val stamped = stampMetadata(draft, id = document.id, timestamp = ServerTimeStamp.now())
        document.set(stamped).addOnFailureListener { error ->
            Log.w(TAG, "Dodajanje vnosa ($collectionName) ni uspelo", error)
        }
    }

    fun remove(id: String) {
        val uid = AuthRepository.currentUser.value?.uid ?: return
        collection(uid).document(id).delete().addOnFailureListener { error ->
            Log.w(TAG, "Brisanje vnosa ($collectionName) ni uspelo", error)
        }
    }

    protected abstract fun stampMetadata(draft: T, id: String, timestamp: Long): T
    private companion object {
        const val TAG = "DataRepository"
    }
}


