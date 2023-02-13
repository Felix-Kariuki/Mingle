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
package com.flexcode.wedate.auth.presentation.profile_images_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.BasicButton
import com.flexcode.wedate.common.composables.LoadingAnimation
import com.flexcode.wedate.common.composables.ScreenTitlesText
import com.flexcode.wedate.common.ext.basicButton
import com.flexcode.wedate.common.ext.fieldModifier

@Composable
fun ProfileImagesScreen(
    modifier: Modifier = Modifier,
    openAndPopUp: (String, String) -> Unit,
    viewModel: ProfileImagesViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val visible: String = if (state.isLoading == "true"){
        "true"
    }else{
        "false"
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenTitlesText(
                text = R.string.profile_image,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .fieldModifier(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 0..2) {
                    var clicked = "profileImage"
                    when (i) {
                        0 -> { clicked += "1" }
                        1 -> { clicked += "2" }
                        2 -> { clicked += "3" }
                    }

                    ClickableToGalleryImage(clicked, viewModel)
                }
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .fieldModifier(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 0..2) {
                    var clicked = "profileImage"
                    when (i) {
                        0 -> { clicked += "4" }
                        1 -> { clicked += "5" }
                        2 -> { clicked += "6" }
                    }
                    ClickableToGalleryImage(clicked, viewModel)
                }
            }

            Row(
                modifier = modifier
                    .weight(1f)
                    .padding(bottom = 30.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                BasicButton(
                    text = R.string.register,
                    modifier = modifier
                        .basicButton()
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    viewModel.onContinueClicked(openAndPopUp)
                }
            }
        }

        if (visible == "true"){
            LoadingAnimation()
        }
    }

}

@Composable
fun ClickableToGalleryImage(clicked: String, viewModel: ProfileImagesViewModel) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        println(imageUri)
        imageUri = uri
        imageUri?.let { viewModel.addImageToFirebaseStorage(it, clicked) }
    }
    Box(
        modifier = Modifier.clip(shape = CircleShape)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = imageUri)
                    .placeholder(R.drawable.ic_add)
                    .build()
            ),
            contentDescription = "add $clicked",
            modifier = Modifier
                .clickable {
                    launcher.launch("image/*")
                }
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}
