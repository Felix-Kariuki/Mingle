package com.flexcode.wedate.chatsscreen.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.domain.usecase.UseCaseContainer
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val useCaseContainer: UseCaseContainer,
    logService: LogService
): BaseViewModel(logService = logService){

    var state = mutableStateOf(ChatScreenState())
        private set

     fun getUserDetails(id:String) {
        viewModelScope.launch {
            useCaseContainer.getUserDetailsUseCase(uid = id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            userDetails = result.data
                        )
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {}
                }
            }
        }
    }
}