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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.flexcode.wedate.common.composables.SwipeRightLeftIcon
import com.flexcode.wedate.common.theme.lightPurple
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
    navigateBack: () -> Unit
) {
    val singapore = LatLng(1.35, 103.87)
    val singapore1 = LatLng(1.35, 103.86)
    val singapore2 = LatLng(1.35, 103.85)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 14f)
    }
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN))
    }
    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings
        ) {
            listOf(
                Marker(
                    state = MarkerState(position = singapore),
                    title = "Singapore",
                    snippet = "Marker in Singapore"
                ),
                Marker(
                    state = MarkerState(position = singapore1),
                    title = "Singapore1",
                    snippet = "Marker in Singapore"
                ),
                Marker(
                    state = MarkerState(position = singapore2),
                    title = "Singapore2",
                    snippet = "Marker in Singapore"
                )
            )
        }

        SwipeRightLeftIcon(
            onClick = { navigateBack() },
            icon = Icons.Default.ArrowBack, contentDesc = "Back",
            modifier = modifier
                .padding(8.dp)
                .background(color = lightPurple, shape = RoundedCornerShape(100.dp)),
            height = 30.dp,
            width = 30.dp,
            paddingValues = PaddingValues(4.dp)
        )
    }
}