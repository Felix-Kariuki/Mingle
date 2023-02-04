package com.flexcode.wedate.auth.presentation.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = ""
)