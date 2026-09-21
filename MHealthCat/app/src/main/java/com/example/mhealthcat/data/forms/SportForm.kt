package com.example.mhealthcat.data.forms

import com.example.mhealthcat.data.repositories.Entry

data class SportForm(
    val rating: Int = 1,
    val hours: Int = 0,
    val minutes: Int = 0,
    val activity: String = "",
    val comment: String = "",
    override val id: String = "",
    override val timestamp: Long = 0L
) : Entry