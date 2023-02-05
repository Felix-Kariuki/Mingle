package com.flexcode.wedate.home.presentation

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
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
                Timber.i("Location..$locationName")
                Timber.i("Location..$longitude")
                Timber.i("Location..$latitude")
                updateUserLocation(
                    locationName,
                    latitude = latitude,
                    longitude = longitude,
                )
            }
        }
        return locationName

    }

    //update user profile.. pass lat long and location name
    private fun updateUserLocation(
        locationName: String, latitude: MutableState<Double>, longitude: MutableState<Double>
    ) {
        viewModelScope.launch {
            useCaseContainer.updateUserProfileInfoUseCase.invoke(
                latitude = latitude,
                longitude = longitude,
                locationName = locationName
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Timber.i("SUCCESS..")
                    }
                    is Resource.Loading -> {
                        Timber.i("LOADING...")
                    }
                    is Resource.Error -> {
                        Timber.i("${result.message}")
                        SnackBarManager.showError("${result.message}")
                    }
                }

            }
        }

    }
}