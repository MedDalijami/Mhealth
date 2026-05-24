package com.example.mhealthcat.forms

data class WellbeingForm(
    val rating: Int = 1,
    val generalFeelings: String = "",
    val generalFears: String = "",
    val somethingGoodThatHappened: String = "",
    val createdAt: Long = System.currentTimeMillis()
)