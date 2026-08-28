package com.example.mhealthcat.models

import android.net.Uri
import java.util.UUID

data class UserProfile(
    val id: String = UUID.randomUUID().toString(),
    val email: String = "",
    val name: String = "",
    val lastName: String = "",
    val profilePictureUri: Uri? = null,
    val createdAt: Long = System.currentTimeMillis()
)
