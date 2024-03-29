/*
 * Copyright 2023 Felix Kariuki.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flexcode.wedate.home.presentation

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.snackbar.SnackBarManager
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedatecompose.network.data.datastore.AuthDataStore
import com.flexcode.wedatecompose.network.data.models.auth.User
import com.flexcode.wedatecompose.network.domain.use_cases.auth.UseCaseContainer
import com.flexcode.wedatecompose.network.domain.use_cases.home.HomeUseCases
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    logService: LogService,
    private val useCaseContainer: UseCaseContainer,
    private val homeUseCases: HomeUseCases,
    private val auth: FirebaseAuth,
    private val dataStore: AuthDataStore
) : BaseViewModel(logService) {

    var state = mutableStateOf(HomeUiState())
        private set

    var initialLocationName = ""

    init {
        // perform validations:
        /** TODO
         * 1st no retrieving if location not allowed.
         * 2nd confirm user logged in
         * validate that you show only interests --- done
         *
         * validate that user cannot be shown people he/she has liked previously
         * delete liked by if the like has stayed for over a week... extend the time
         * exponentially as userbase increases.
         *
         * calculate liked by and show user, delete liked by
         *
         */

        getUserFilters()
        getAllUsers()
        // getLikedBy()
    }

    fun getLocationName(
        context: Context,
        latitude: MutableState<Double>,
        longitude: MutableState<Double>
    ): String {
        var locationName = initialLocationName
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude.value, longitude.value, 1)

            if (addresses != null && addresses.size > 0) {
                val address: Address = addresses[0]
                locationName = (
                    if (address.locality != null) address.locality
                    else address.countryName
                    ).toString()

                if (locationName != "") {
                    if (locationName != initialLocationName) {
                        updateUserLocation(
                            locationName,
                            latitude = latitude.value.toString(),
                            longitude = longitude.value.toString()
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e("Error: Couldn't get location name reason $e")
        }
        return locationName
    }

    private fun updateUserLocation(
        locationName: String,
        latitude: String,
        longitude: String
    ) {
        viewModelScope.launch {
            homeUseCases.updateUserProfileInfoUseCase.invoke(
                latitude = latitude,
                longitude = longitude,
                locationName = locationName
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {}
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.i("${result.message}")
                        SnackBarManager.showError("${result.message}")
                    }
                }
            }
        }
    }

    private fun getAllUsers() {
        viewModelScope.launch {
            homeUseCases.getAllUsersUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value =
                            state.value.copy(
                                potentialMatches = result.data as MutableList<User>
                            )
                        state.value = state.value.copy(isEmpty = false)
                    }

                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("USERS ERROR::: ${result.message}")
                        SnackBarManager.showError("${result.message}")
                    }
                }
            }
        }
    }

    fun getUid(): String {
        return auth.uid!!
    }

    private fun getUserFilters() {
        viewModelScope.launch {
            useCaseContainer.getUserDetailsUseCase.invoke(getUid()).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        saveUserDetails(result.data)
                        state.value = state.value.copy(userDetails = result.data)
                        initialLocationName = result.data?.locationName ?: ""
                        when (result.data?.interestedIn) {
                            "Men" -> {
                                state.value = state.value.copy(interestedIn = "Male")
                            }

                            "Women" -> {
                                state.value = state.value.copy(interestedIn = "Female")
                            }

                            else -> {
                                state.value = state.value.copy(interestedIn = "Everyone")
                            }
                        }
                        val userAge = result.data?.years
                        val yob = result.data?.dateOfBirth
                        delay(3.seconds)
                        calculateAge(userAge, yob)
                    }

                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e(
                            "INTERESTS IN ERROR::: ${result.message}  + ++  " +
                                "${result.data}"
                        )
                    }
                }
            }
        }
    }

    private fun saveUserDetails(data: User?) {
        launchCatching {
            data?.latitude?.let { dataStore.saveUserLatitude(it) }
            data?.longitude?.let { dataStore.saveUserLongitude(it) }
        }
    }

    private fun calculateAge(userAge: String?, yob: String?) {
        try {
            val yearOfBirth = "$yob 00:00"
            val today = Date()
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
            val formattedYearOfBirth = sdf.parse(yearOfBirth)
            val years = (today.time - formattedYearOfBirth.time) / 31536000000
            if (years.toInt() > userAge.toString().toInt()) {
                updateUserAge(years.toInt())
            }
        } catch (e: Exception) {
            Timber.e("The error is $e    details:: $yob  $userAge ")
        }
    }

    private fun updateUserAge(years: Int) {
        viewModelScope.launch {
            homeUseCases.updateUserAgeUseCase.invoke(years.toString()).collect { result ->
                when (result) {
                    is Resource.Success -> {}
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("Update User Age ERROR::: ${result.message}")
                    }
                }
            }
        }
    }

    fun saveLikeToCrush(
        crushUserId: String,
        firstName: String,
        locationName: String,
        years: String,
        lat: String,
        long: String,
        profileImage: String,
        matched: Boolean
    ) {
        viewModelScope.launch {
            homeUseCases.saveLikeUseCase.invoke(
                crushUserId,
                firstName,
                locationName,
                years,
                lat,
                long,
                profileImage,
                matched
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {}
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("SAVE CRUSH ERROR::: ${result.message}")
                    }
                }
            }
        }
    }

    fun saveMatchToCrush(
        crushUserId: String,
        firstName: String,
        locationName: String,
        years: String,
        lat: String,
        long: String,
        profileImage: String
    ) {
        viewModelScope.launch {
            homeUseCases.saveMatchUseCase.invoke(
                crushUserId,
                firstName,
                locationName,
                years,
                lat,
                long,
                profileImage
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {}
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("SAVE MATCH ERROR::: ${result.message}")
                    }
                }
            }
        }
    }

    fun saveMatchToCurrentUser(
        crushUserId: String,
        firstName: String,
        locationName: String,
        years: String,
        lat: String,
        long: String,
        profileImage: String
    ) {
        viewModelScope.launch {
            homeUseCases.saveMatchToCurrentUserUseCase.invoke(
                crushUserId,
                firstName,
                locationName,
                years,
                lat,
                long,
                profileImage
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {}
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("SAVE MATCH ERROR::: ${result.message}")
                    }
                }
            }
        }
    }

    fun deleteLikedByFromMe(userLikeId: String) {
        viewModelScope.launch {
            homeUseCases.deleteLikedByFromUseCase.invoke(userLikeId).collect { result ->
                when (result) {
                    is Resource.Success -> {}
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("DELETE LIKEDBy ERROR::: ${result.message}")
                    }
                }
            }
        }
    }

    /*fun getLikedBy(){
        viewModelScope.launch {
            useCaseContainer.getLikedByUseCase.invoke().collect{result->
                when (result) {
                    is Resource.Success -> {
                        Timber.i("SUCCESS LIKES:: ${result.data}")
                        //state.value  = state.value.copy(likedBy = result.data as
                        MutableList<Likes>)

                        Timber.i("SUCCESS LIKES:: ${(result.data)?.first()}")
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("GET LIKED BY ERROR::: ${result.message}")
                    }
                }
            }
        }
    }*/
}
