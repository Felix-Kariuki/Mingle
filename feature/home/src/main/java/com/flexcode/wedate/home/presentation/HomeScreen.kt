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

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.BasicText
import com.flexcode.wedate.common.composables.SearchingPotentialMatches
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.home.PersonsCardStack
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
        permissionState,
        context,
        fusedLocationClient,
        longitude,
        latitude,
        viewModel
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
                    items = (
                        state.potentialMatches.filter { user ->
                            user.id != viewModel.getUid() && !user.likedBy!!.contains(
                                viewModel.getUid()
                            ) && user.accountStatus != "DELETED"
                        }
                        ).shuffled(),
                    onEmptyStack = {
                        state.isEmpty = false
                    },
                    viewModel = viewModel,
                    context = context
                )
            } else {
                PersonsCardStack(
                    items = state.potentialMatches.filter { user ->
                        user.id != viewModel.getUid() && user.gender == state.interestedIn &&
                            !user.likedBy!!.contains(viewModel.getUid()) &&
                            user.accountStatus != "DELETED"
                    }.shuffled(),
                    onEmptyStack = {
                        state.isEmpty = false
                    },
                    viewModel = viewModel,
                    context = context
                )
            }
        } else {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
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
        }
    }
}
