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
package com.flexcode.wedate.maps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.admirers.presentation.AdmirersViewModel
import com.flexcode.wedate.common.composables.SwipeRightLeftIcon
import com.flexcode.wedate.common.theme.lightPurple
import com.flexcode.wedatecompose.network.data.models.home.Likes
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapsScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: AdmirersViewModel = hiltViewModel()
) {
    val state by viewModel.state

    val myLocation = viewModel.getUserLocation()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(myLocation, 2f)
    }

    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    Box(Modifier.fillMaxSize()) {
        val yourLocation: List<Likes> = listOf(
            Likes(
                id = state.userDetails?.id ?: "",
                date = null,
                firstName = state.userDetails?.firstName ?: "",
                locationName = state.userDetails?.locationName ?: "",
                years = state.userDetails?.years ?: "",
                lat = myLocation.latitude.toString(),
                long = myLocation.longitude.toString(),
                profileImage = state.userDetails?.profileImage?.profileImage1 ?: "",
                matched = false
            )
        )

        // change the icon of the userr to put it as their profile picture

        if (state.admirers.isNotEmpty()) {
            val allAdmirers = state.admirers as MutableList
            allAdmirers.addAll(yourLocation)
            GoogleMap(
                modifier = modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                properties = properties,
                uiSettings = uiSettings
            ) {
                for (i in allAdmirers) {
                    if (!i.matched) {
                        val lat = i.lat.toDouble()
                        val longitude = i.long.toDouble()
                        val latlng = LatLng(lat, longitude)
                        if (lat != 0.0 && longitude != 0.0) {
                            val name = if (i.firstName != state.userDetails?.firstName) {
                                "Liked by ${i.firstName}"
                            } else "Your Location"
                            val title = if (i.firstName != state.userDetails?.firstName) {
                                i.firstName
                            } else "You"
                            Marker(
                                state = MarkerState(position = latlng),
                                title = title,
                                snippet = name
                                // icon = toBitmapDescriptor(i.profileImage)
                            )
                        }
                    }
                }
            }
        } else {
            GoogleMap(
                modifier = modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                properties = properties,
                uiSettings = uiSettings
            ) {
                Marker(
                    state = MarkerState(position = myLocation),
                    title = "You",
                    snippet = "Your Location"
                    // icon = bitmap?.let { BitmapDescriptorFactory.fromBitmap(it) }
                )
            }
        }
    }

    SwipeRightLeftIcon(
        onClick = { navigateBack() },
        icon = Icons.Default.ArrowBack,
        contentDesc = "Back",
        modifier = modifier
            .padding(8.dp)
            .background(color = lightPurple, shape = RoundedCornerShape(100.dp)),
        height = 30.dp,
        width = 30.dp,
        paddingValues = PaddingValues(4.dp),
        circleColor = lightPurple
    )
}
