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
package com.flexcode.wedate.profileedit.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.flexcode.wedate.auth.presentation.profile_images_screen.ProfileImagesViewModel
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.R.drawable as AppIcon
import com.flexcode.wedate.common.composables.*
import com.flexcode.wedate.common.ext.fieldModifier
import com.flexcode.wedate.common.theme.lightPurple

@Composable
fun EditProfileScreen(
    navigateToProfileDetails: () -> Unit,
    modifier: Modifier = Modifier,
    profileDetailsViewModel: ProfileImagesViewModel = hiltViewModel(),
    editProfileViewModel: EditProfileViewModel = hiltViewModel()
) {
    val profileState by profileDetailsViewModel.state
    val uiState by editProfileViewModel.state
    val visible: String = if (profileState.isLoading == "true") {
        "true"
    } else {
        "false"
    }
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.padding(2.dp),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { editProfileViewModel.updateUserProfile(uiState.userBio) },
                backgroundColor = lightPurple,
                contentColor = Color.White,
                icon = {
                    Icon(imageVector = Icons.Default.Done, contentDescription = "Edit Profile")
                },
                text = {
                    BasicText(
                        text = "Save Changes",
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start
                ) {
                    SwipeRightLeftIcon(
                        onClick = { navigateToProfileDetails() },
                        icon = Icons.Default.ArrowBackIos,
                        contentDesc = "Back",
                        height = 30.dp,
                        width = 30.dp,
                        paddingValues = PaddingValues(start = 10.dp, 0.dp),
                        tint = Color.Black
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        ResultText(
                            text = "Edit Profile",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        )
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
                        var imageUrl = ""
                        when (i) {
                            0 -> {
                                clicked += "1"
                                imageUrl =
                                    "${profileState.userDetails?.profileImage?.profileImage1}"
                            }

                            1 -> {
                                clicked += "2"
                                imageUrl =
                                    "${profileState.userDetails?.profileImage?.profileImage2}"
                            }

                            2 -> {
                                clicked += "3"
                                imageUrl =
                                    "${profileState.userDetails?.profileImage?.profileImage3}"
                            }
                        }
                        ClickableImageCard(clicked, profileDetailsViewModel, imageUrl)
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
                        var imageUrl = ""
                        when (i) {
                            0 -> {
                                clicked += "4"
                                imageUrl =
                                    "${profileState.userDetails?.profileImage?.profileImage4}"
                            }

                            1 -> {
                                clicked += "5"
                                imageUrl =
                                    "${profileState.userDetails?.profileImage?.profileImage5}"
                            }

                            2 -> {
                                clicked += "6"
                                imageUrl =
                                    "${profileState.userDetails?.profileImage?.profileImage6}"
                            }
                        }
                        ClickableImageCard(clicked, profileDetailsViewModel, imageUrl)
                    }
                }

                BasicText(
                    text = R.string.about_me,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                BioField(
                    value = uiState.userBio,
                    onNewValue = editProfileViewModel::onUserBioChange,
                    modifier = modifier.offset(y = (-8).dp)
                )
            }

            if (visible == "true") {
                LoadingAnimation()
            }
        }
    }
}

@Composable
fun ClickableImageCard(
    clicked: String,
    viewModel: ProfileImagesViewModel,
    imageUrl: String
) {
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
    val data = if (imageUri != null) {
        imageUri
    } else if (imageUrl != "") {
        imageUrl
    } else {
        AppIcon.ic_add
    }

    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(
            topEnd = 8.dp,
            topStart = 8.dp,
            bottomEnd = 8.dp,
            bottomStart = 8.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data)
                .crossfade(true)
                .build(),
            placeholder = painterResource(AppIcon.ic_add),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(115.dp)
                .height(140.dp)
                .clickable {
                    launcher.launch("image/*")
                }
        )
    }
}
