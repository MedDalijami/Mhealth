package com.example.mhealthcat.data.repositories

interface Entry {
    val id: String
    val timestamp: Long
    val rating: Int
}