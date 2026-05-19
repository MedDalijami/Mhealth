package com.example.mhealthcat.forms

data class SignUpForm(
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val passwordRepeat: String = ""
) {
    val isValidEmail get() = email.contains("@") && email.contains(".")
    val isValidPassword get() = password.length >= 8 && password.any { it.isDigit() }
    val isValidPasswordRepeat get() = passwordRepeat == password && isValidPassword
    val isValid get() = name.isNotEmpty() && lastName.isNotEmpty()
            && isValidEmail && isValidPassword && isValidPasswordRepeat
}