package com.flexcode.wedate.auth.presentation.login

import androidx.compose.runtime.mutableStateOf
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.common.navigation.HOME_SCREEN_CONTENT
import com.flexcode.wedate.common.navigation.LOGIN_SCREEN
import com.flexcode.wedate.common.navigation.REGISTER_SCREEN
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.ext.isValidEmail
import com.flexcode.wedate.common.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.flexcode.wedate.common.R.string as AppText

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
        if (authRepository.hasUser && !authRepository.userIsAnonymous) openAndPopUp(HOME_SCREEN_CONTENT, LOGIN_SCREEN)
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