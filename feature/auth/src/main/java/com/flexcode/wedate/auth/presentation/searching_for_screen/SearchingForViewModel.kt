package com.flexcode.wedate.auth.presentation.searching_for_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.data.local.datastore.AuthDataStore
import com.flexcode.wedate.auth.domain.usecase.UseCaseContainer
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.navigation.PROFILE_IMAGES_SCREEN
import com.flexcode.wedate.common.navigation.SEARCHING_FOR_SCREEN
import com.flexcode.wedate.common.snackbar.SnackBarManager
import com.flexcode.wedate.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchingForViewModel @Inject constructor(
    logService: LogService,
    private val dataStore: AuthDataStore,
    private val useCase: UseCaseContainer
) : BaseViewModel(logService) {

    var email = ""
    var firstName = ""
    var phone = ""
    var gender = ""
    var interest = ""
    var yearBirth = ""
    var password = ""
    var age = ""

    init {
        viewModelScope.launch {
            dataStore.getUserGender.collect {
                gender = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getUserInterest.collect {
                interest = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getYearOfBirth.collect {
                yearBirth = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getUserName.collect {
                firstName = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getUserEmail.collect {
                email = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getUserPhone.collect {
                phone = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getUserPwd.collect {
                password = it.substring(1, it.length - 1)
            }
        }
        viewModelScope.launch {
            dataStore.getUserAge.collect{
                age = it.substring(1,it.length -1)
            }
        }
    }

    private val _selectSearchForOption = mutableStateOf("[Relationship]")
    val selectSearchForOption: State<String> = _selectSearchForOption
    fun setSelectSearchForOption(value: String) {
        _selectSearchForOption.value = value

    }

    fun registerUser(openAndPoUp: (String,String) -> Unit) {
        viewModelScope.launch {
            useCase.registerUseCase(
                firstName = firstName,
                phoneNumber = phone,
                email = email,
                password = password,
                dateOfBirth = yearBirth,
                monthOfBirth = "",
                yearOfBirth = "",
                years = age,
                gender = gender,
                interestedIn = interest,
                searchingFor = selectSearchForOption.value

            ).collect {result->
                when(result){
                    is Resource.Success -> {
                        launchCatching {
                            openAndPoUp(PROFILE_IMAGES_SCREEN, SEARCHING_FOR_SCREEN)
                        }
                    }
                    is Resource.Loading -> {

                    }

                    is Resource.Error -> {
                        SnackBarManager.showError(result.message.toString())
                    }
                }
            }
        }
    }
}