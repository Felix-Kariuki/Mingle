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
    val myLocation = LatLng(-1.286389, 36.817223)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(myLocation, 5f)
    }
    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings
        ) {
            for (i in state.admirers) {
                if (!i.matched) {
                    val lat = i.lat.toDouble()
                    val longitude = i.long.toDouble()
                    val latlng = LatLng(lat, longitude)
                    if (lat != 0.0 && longitude != 0.0) {
                        Marker(
                            state = MarkerState(position = latlng),
                            title = i.firstName,
                            snippet = "Liked by ${i.firstName}"
                            // icon = toBitmapDescriptor(i.profileImage)
                        )
                    }
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
            paddingValues = PaddingValues(4.dp)
        )
    }
}
/*
fun toBitmapDescriptor(imageUrl:String): BitmapDescriptor? {

    val url = URL(imageUrl)
    val connection = url.openConnection() as HttpURLConnection
    connection.doInput = true
    connection.connect()
    val input = connection.inputStream

    val options = BitmapFactory.Options()
    options.inPreferredConfig = Bitmap.Config.ARGB_8888

    val bitmap = BitmapFactory.decodeStream(input, null, options)

    val descriptor = bitmap?.let { BitmapDescriptorFactory.fromBitmap(it) }

    return descriptor
}*/
