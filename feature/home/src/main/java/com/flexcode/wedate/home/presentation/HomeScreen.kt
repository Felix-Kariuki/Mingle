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
@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.flexcode.wedate.home.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.BasicText
import com.flexcode.wedate.common.composables.SearchingPotentialMatches
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.home.TwyperScreen
import com.flexcode.wedate.home.location.GetCurrentLocation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
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

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Timber.i("Permission Granted")
        } else {
            Timber.i("Permission Denied")
        }
    }

    val permissionCheckResult =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
        }

    LaunchedEffect(key1 = true) {
        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat
                .checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                Timber.i("Notification Permission granted")
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    GetCurrentLocation(
        permissionState,
        context,
        fusedLocationClient,
        longitude,
        latitude,
        viewModel
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .clip(
                shape = RoundedCornerShape(
                    topEnd = 130.dp,
                    bottomEnd = 25.dp,
                    topStart = 25.dp,
                    bottomStart = 25.dp
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!state.isEmpty) {
            if (state.interestedIn == "Everyone") {
                TwyperScreen(
                    items = (state.potentialMatches.filter { user ->
                            user.id != viewModel.getUid() && !user.likedBy!!.contains(
                                viewModel.getUid()
                            ) && user.accountStatus != "DELETED"
                        }).toMutableList(),
                    modifier =modifier,
                    viewModel =viewModel,
                    context =context,

                )
//                PersonsCardStack(
//                    items = (
//                        state.potentialMatches.filter { user ->
//                            user.id != viewModel.getUid() && !user.likedBy!!.contains(
//                                viewModel.getUid()
//                            ) && user.accountStatus != "DELETED"
//                        }
//                        ),
//                    onEmptyStack = {
//                        state.isEmpty = false
//                    },
//                    viewModel = viewModel,
//                    context = context
//                )
            } else {
                TwyperScreen(
                   items = (state.potentialMatches.filter { user ->
                        user.id != viewModel.getUid() && user.gender == state.interestedIn &&
                            !user.likedBy!!.contains(viewModel.getUid()) &&
                            user.accountStatus != "DELETED"
                    }.shuffled()).toMutableList(),
                    modifier =modifier,
                    viewModel =viewModel,
                    context =context,
                )
//                PersonsCardStack(
//                    items = state.potentialMatches.filter { user ->
//                        user.id != viewModel.getUid() && user.gender == state.interestedIn &&
//                            !user.likedBy!!.contains(viewModel.getUid()) &&
//                            user.accountStatus != "DELETED"
//                    }.shuffled(),
//                    onEmptyStack = {
//                        state.isEmpty = false
//                    },
//                    viewModel = viewModel,
//                    context = context
//                )
            }
        } else {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                HomeLoading()
            }
        }
    }
}

@Composable
fun HomeLoading(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SearchingPotentialMatches()
            BasicText(
                text = R.string.searching_potential_matches,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = deepBrown
            )
        }

}