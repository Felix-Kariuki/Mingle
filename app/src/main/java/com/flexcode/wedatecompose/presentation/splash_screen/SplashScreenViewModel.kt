package com.flexcode.wedatecompose.presentation.splash_screen

import androidx.compose.runtime.mutableStateOf
import com.flexcode.wedatecompose.common.BaseViewModel
import com.flexcode.wedatecompose.common.navigation.LOGIN_SCREEN
import com.flexcode.wedatecompose.common.navigation.SPLASH_SCREEN
import com.flexcode.wedatecompose.data.LogService
import com.flexcode.wedatecompose.data.repository.AuthRepository
import com.google.firebase.FirebaseException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    logService: LogService
) : BaseViewModel(logService){
    val showError = mutableStateOf(false)


    fun onAppStart(openAndPopUp: (String,String) -> Unit){
        showError.value = false
        if (authRepository.hasUser) openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
        else createAnonymousAccount(openAndPopUp)//openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)//createAnonymousAccount(openAndPopUp)
    }

    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
        launchCatching(snackBar = false) {
            try {
                authRepository.createAnonymousAccount()
            } catch (exception: FirebaseException){
                throw exception
            }
            openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
        }
    }
}