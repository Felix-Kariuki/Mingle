package com.flexcode.wedate.admirers.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.home.domain.use_cases.HomeUseCases
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AdmirersViewModel @Inject constructor(
    logService: LogService,
    private val homeUseCases: HomeUseCases,
    private val auth: FirebaseAuth,
) : BaseViewModel(logService) {

    var state = mutableStateOf(AdmirersState())
    private set

    init {
        getAllLikedBy(getUid())
    }

    private fun getAllLikedBy(currentUserId:String) {
        viewModelScope.launch {
            homeUseCases.getAllLikedByUseCase.invoke(currentUserId).collect {result->
                when(result){
                    is Resource.Success -> {
                        state.value = result.data?.let { state.value.copy(admirers = it) }!!
                    }
                    is Resource.Loading -> {}
                    is Resource.Error ->{
                        Timber.e("LIKES ERROR::: ${result.message}")
                    }
                }
            }
        }
    }

    fun getUid(): String {
        return auth.uid!!
    }

    /*private fun getAdmirers() {
        viewModelScope.launch {
            useCaseContainer.getUserDetailsUseCase.invoke().collect {result->
                when (result) {
                    is Resource.Success -> {
                        state.value = state.value.copy(admirers = result.data?.likedBy)
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("INTERESTS IN ERROR::: ${result.message}")
                    }
                }
            }
        }
    }*/

}