package com.flexcode.wedate.home.presentation

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.auth.domain.usecase.UseCaseContainer
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.snackbar.SnackBarManager
import com.flexcode.wedate.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    logService: LogService,
    private val useCaseContainer: UseCaseContainer
) : BaseViewModel(logService) {


    var state = mutableStateOf(HomeUiState())
        private set

    init {
        //perform validations:
        /** TODO
         * 1st no retrieving if location not allowed.
         * 2nd confirm user logged in
         * validate that you show only interests
         *
         */
        getAllUsers()
    }

    fun getLocationName(
        context: Context,
        latitude: MutableState<Double>,
        longitude: MutableState<Double>
    ): String {

        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude.value, longitude.value, 1)
        var locationName = ""
        if (addresses != null && addresses.size > 0) {
            val address: Address = addresses[0]
            locationName = address.locality
            if (locationName!=""){
                updateUserLocation(
                    locationName,
                    latitude = latitude.value.toString(),
                    longitude = longitude.value.toString(),
                )
            }
        }
        return locationName

    }

    //update user profile.. pass lat long and location name
    private fun updateUserLocation(
        locationName: String, latitude: String, longitude: String
    ) {
        viewModelScope.launch {
            useCaseContainer.updateUserProfileInfoUseCase.invoke(
                latitude = latitude,
                longitude = longitude,
                locationName = locationName
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Error -> {
                        Timber.i("${result.message}")
                        SnackBarManager.showError("${result.message}")
                    }
                }

            }
        }

    }

    fun getAllUsers(){
        viewModelScope.launch {
            useCaseContainer.getAllUsersUseCase.invoke().collect{result ->
                when(result){
                    is Resource.Success -> {
                        state.value = state.value.copy(potentialMatches = result.data as MutableList<User>)
                        state.value = state.value.copy(isEmpty = false)
                        Timber.i("USERS:: ${result.data}")
                    }
                    is Resource.Loading -> {
                        Timber.i("Loading")
                    }
                    is Resource.Error -> {
                        Timber.e("ERROR :: ${result.message}")
                        SnackBarManager.showError("${result.message}")
                    }
                }
            }
        }
    }
}