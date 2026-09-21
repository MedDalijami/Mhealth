package com.example.mhealthcat.data.forms

import com.example.mhealthcat.data.repositories.Entry

data class WellbeingForm(
    val rating: Int = 1,
    val generalFeelings: String = "",
    val generalFears: String = "",
    val somethingGoodThatHappened: String = "",
    override val id: String = "",
    override val timestamp: Long = 0L
) : Entry