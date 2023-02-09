package com.flexcode.wedate.auth.presentation.splash_screen

import androidx.compose.runtime.mutableStateOf
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.navigation.LOGIN_SCREEN
import com.flexcode.wedate.common.navigation.SPLASH_SCREEN
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
        else openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
    }
}