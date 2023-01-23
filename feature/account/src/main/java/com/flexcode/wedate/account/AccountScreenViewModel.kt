package com.flexcode.wedate.account

import androidx.compose.runtime.mutableStateOf
import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.auth.domain.repository.StoreRegistrationRepository
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.ext.idFromParameter
import com.flexcode.wedate.common.navigation.USER_DEFAULT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val storeRegistrationRepository: StoreRegistrationRepository,
    private val authRepository: AuthRepository,
    logService: LogService
) : BaseViewModel(logService) {

    val user = mutableStateOf(User())
    fun getUserId(): String {
        return authRepository.currentUserId
    }

    suspend fun getUserDetails(userId: String) {
        user.value = storeRegistrationRepository.getUserDetails(userId) ?: User()
        Timber.d("DETAILS:: ${storeRegistrationRepository.getUserDetails(userId)}")
    }
}