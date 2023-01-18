package com.flexcode.wedate.auth.presentation.searching_for_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.navigation.PROFILE_IMAGES_SCREEN
import com.flexcode.wedate.auth.data.local.datastore.AuthDataStore
import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.auth.domain.repository.StoreRegistrationRepository
import com.flexcode.wedate.common.data.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchingForViewModel @Inject constructor(
    logService: LogService,
    private val dataStore: AuthDataStore,
    private val authRepository: AuthRepository,
    private val storeRegistrationRepository: StoreRegistrationRepository
) : BaseViewModel(logService) {

    var email = ""
    var firstName = ""
    var phone = ""
    var gender = ""
    var interest = ""
    var yearBirth = ""
    var password = ""

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
        //viewModelScope.launch { dataStore.getUserSearchingFor.collect { Log.d("YEAR_OF_SEARCHING", it) } }
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


        ///don't save searching for?  ... add it directly from selected value
    }

    private val _selectSearchForOption = mutableStateOf("Relationship")
    val selectSearchForOption: State<String> = _selectSearchForOption
    fun setSelectSearchForOption(value: String) {
        _selectSearchForOption.value = value

        //saveSearchingFor(selectSearchForOption.value)
    }

    fun onContinueClicked(openScreen: (String) -> Unit) {
        //registerUser and save info
        launchCatching {
            //have it as open and popup
            authRepository.register(email, password)

            val userInfo = User(
                id = "",
                isAnonymous = false,
                firstName = firstName,
                phoneNumber = phone,
                email = email,
                password = password,
                dateOfBirth = yearBirth,
                monthOfBirth = "",
                yearOfBirth = "",
                years = "",
                gender = gender,
                interestedIn = interest,
                searchingFor = selectSearchForOption.value,
                profileImage1 = "",
                profileImage2 = "",
                profileImage3 = "",
                profileImage4 = "",
                profileImage5 = "",
                profileImage6 = "",
                isFree = false
            )
            storeRegistrationRepository.saveUserDetails(userInfo)

            openScreen(PROFILE_IMAGES_SCREEN)
        }


    }
}