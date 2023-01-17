package com.flexcode.wedatecompose.presentation.login

import androidx.compose.runtime.mutableStateOf
import com.flexcode.wedatecompose.common.BaseViewModel
import com.flexcode.wedatecompose.common.ext.isValidEmail
import com.flexcode.wedatecompose.common.navigation.*
import com.flexcode.wedatecompose.common.snackbar.SnackBarManager
import com.flexcode.wedatecompose.data.LogService
import com.flexcode.wedatecompose.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.flexcode.wedatecompose.R.string as AppText

@HiltViewModel
class LoginViewModel @Inject constructor(
    logService: LogService,
    private val authRepository: AuthRepository
) : BaseViewModel(logService) {
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(value: String) {
        uiState.value = uiState.value.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        uiState.value = uiState.value.copy(password = value)
    }


    fun onAppStart(openAndPopUp: (String,String) -> Unit){
        if (authRepository.hasUser) openAndPopUp(HOME_SCREEN_CONTENT,LOGIN_SCREEN)
    }

    fun onLoginClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackBarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackBarManager.showMessage(AppText.password_error)
            return
        }

        launchCatching {
            authRepository.login(email, password)
            openAndPopUp(HOME_SCREEN_CONTENT, LOGIN_SCREEN)
        }
    }

    fun onRegisterClicked(openScreen: (String) -> Unit){
        launchCatching { openScreen(REGISTER_SCREEN) }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackBarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
            authRepository.sendRecoveryEmail(email)
            SnackBarManager.showMessage(AppText.recovery_email_sent)
        }
    }

}