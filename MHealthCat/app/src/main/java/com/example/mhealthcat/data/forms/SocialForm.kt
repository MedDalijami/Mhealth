package com.example.mhealthcat.data.forms

import com.example.mhealthcat.data.repositories.Entry

data class SocialForm(
    val socialInteraction: String = "",
    val people: String = "Prijatelji",
    val numberOfPeople: Int = 1,
    val comment: String = "",
    val rating: Int = 1,
    val hours: Int = 0,
    val minutes: Int = 0,
    override val id: String = "",
    override val timestamp: Long = 0L
) : Entry
