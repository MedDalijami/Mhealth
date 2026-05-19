package com.example.mhealthcat.models

import java.util.UUID

data class UserProfile(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val name: String,
    val lastName: String,
    val password: String,
    val createdAt: Long = System.currentTimeMillis()
)
