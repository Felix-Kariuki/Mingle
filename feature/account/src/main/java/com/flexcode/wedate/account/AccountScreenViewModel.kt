package com.flexcode.wedate.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.auth.domain.usecase.UseCaseContainer
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.navigation.LOVE_CALCULATOR_SCREEN
import com.flexcode.wedate.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    private val useCaseContainer: UseCaseContainer,
    logService: LogService
) : BaseViewModel(logService) {

    var state = mutableStateOf(AccountState())
        private set

    suspend fun getUserDetails() {

        viewModelScope.launch {
            useCaseContainer.getUserDetailsUseCase().collect{result->
                when(result){
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            userDetails = result.data
                        )
                    }
                    is  Resource.Loading -> {
                        state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        state.value = state.value.copy(isLoading = false)
                    }
                }
            }
        }
    }

    fun onLoveCalculatorClick(openScreen: (String) -> Unit){
        launchCatching { openScreen(LOVE_CALCULATOR_SCREEN) }
    }
}