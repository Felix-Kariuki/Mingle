package com.flexcode.wedate.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.auth.domain.usecase.UseCaseContainer
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val authRepository: AuthRepository,
    private val useCaseContainer: UseCaseContainer,
) :BaseViewModel(logService){

    var state = mutableStateOf(SettingsState())
        private set

    fun signOut(){
        authRepository.signOut()
    }

    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            useCaseContainer.getUserDetailsUseCase().collect{result->
                when(result){
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            userDetails = result.data
                        )
                    }
                    is  Resource.Loading -> {}
                    is Resource.Error -> {}
                }
            }
        }
    }

}