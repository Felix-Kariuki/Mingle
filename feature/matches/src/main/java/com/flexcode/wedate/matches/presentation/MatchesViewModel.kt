package com.flexcode.wedate.matches.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.matches.domain.use_case.UseCaseContainer
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    logService: LogService,
    private val auth: FirebaseAuth,
    private val useCases: UseCaseContainer
) : BaseViewModel(logService) {
    var state = mutableStateOf(MatchesState())
        private set

    private val searchValue
        get() = state.value.searchValue

    fun onSearchValueChange(value: String) {
        state.value = state.value.copy(searchValue = value)
    }

    init {
        getMatches()
    }

    private fun getMatches() {
        viewModelScope.launch {
            useCases.getAllUserMatchesUseCase.invoke().collect {result->
                when(result){
                    is Resource.Success -> {
                        state.value = result.data?.let { state.value.copy(matches = it) }!!
                    }
                    is Resource.Loading -> {}
                    is Resource.Error ->{
                        Timber.e("MATCHES ERROR::: ${result.message}")
                    }
                }
            }
        }
    }

    fun getUid(): String {
        return auth.uid!!
    }

}