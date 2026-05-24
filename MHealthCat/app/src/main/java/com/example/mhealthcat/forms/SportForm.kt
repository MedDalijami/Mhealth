package com.example.mhealthcat.forms

data class SportForm(
    val rating: Int = 1,
    val hours: Int = 0,
    val minutes: Int = 0,
    val activity: String = "",
    val comment: String = "",
    val createdAt: Long = System.currentTimeMillis()
)