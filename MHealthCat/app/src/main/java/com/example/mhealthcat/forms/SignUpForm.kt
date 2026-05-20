package com.example.mhealthcat.forms

import android.net.Uri

data class SignUpForm(
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val passwordRepeat: String = "",
    val profilePictureUri: Uri? = null
) {
}