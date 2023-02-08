package com.flexcode.wedate.home.presentation

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.common.composables.ResultText
import com.flexcode.wedate.home.PersonsCardStack
import com.flexcode.wedate.home.data.model.Likes
import com.flexcode.wedate.home.location.GetCurrentLocation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.state
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val longitude: MutableState<Double> = remember {
        mutableStateOf(0.00)
    }
    val latitude: MutableState<Double> = remember {
        mutableStateOf(0.00)
    }

    GetCurrentLocation(
        permissionState, context, fusedLocationClient, longitude, latitude, viewModel
    )

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!state.isEmpty) {
            if (state.interestedIn == "Everyone") {
                PersonsCardStack(
                    items = (state.potentialMatches.filter { user ->
                        user.id != viewModel.getUid() && !user.likedBy!!.contains(viewModel.getUid())
                    }).shuffled(),
                    onEmptyStack = {
                        state.isEmpty = false
                    },
                    viewModel = viewModel
                )
            } else {
                PersonsCardStack(
                    items = state.potentialMatches.filter { user ->
                        user.id != viewModel.getUid() && user.gender == state.interestedIn &&
                                !user.likedBy!!.contains(viewModel.getUid())
                    }.shuffled(),
                    onEmptyStack = {
                        state.isEmpty = false
                    },
                    viewModel = viewModel,
                )

            }
        } else {
            ResultText(
                text = "No more People Within your range adjust settings..",
                fontWeight = FontWeight.Bold,
            )
        }
    }

}