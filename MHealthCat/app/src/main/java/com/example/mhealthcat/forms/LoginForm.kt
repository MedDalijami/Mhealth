package com.example.mhealthcat.forms

data class LogInForm(
    val email:  String = "",
    val password: String = ""
) {
    val isValidEmail get() = email.contains("@") && email.contains(".")
    val isValidPassword get() = password.length >= 8 && password.any { it.isDigit() }
    val isValid get() = isValidEmail && isValidPassword
}
