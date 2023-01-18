package com.flexcode.wedate.auth.presentation.register

data class RegisterUiState(
    val firstName : String = "",
    val phoneNumber : String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)
