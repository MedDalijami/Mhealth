package com.example.mhealthcat.forms

data class SleepForm(
    val rating: Int = 1,
    val hours: Int = 0,
    val minutes: Int = 0,
    val comment: String = ""
)