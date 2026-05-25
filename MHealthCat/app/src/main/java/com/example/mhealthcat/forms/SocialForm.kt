package com.example.mhealthcat.forms

data class SocialForm(
    val socialInteraction: String = "",
    val people: String = "Prijatelji",
    val numberOfPeople: Int = 1,
    val comment: String = "",
    val rating: Int = 1,
    val hours: Int = 0,
    val minutes: Int = 0,
    val createdAt: Long = System.currentTimeMillis()

) {
}