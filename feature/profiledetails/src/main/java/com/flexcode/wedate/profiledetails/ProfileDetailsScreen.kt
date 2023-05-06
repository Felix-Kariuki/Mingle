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
package com.flexcode.wedate.profiledetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.common.R.string as AppText
import com.flexcode.wedate.common.composables.BasicText
import com.flexcode.wedate.common.composables.DotsIndicator
import com.flexcode.wedate.common.composables.ResultText
import com.flexcode.wedate.common.composables.SliderView
import com.flexcode.wedate.common.extestions.textPadding
import com.flexcode.wedate.common.theme.lightPurple
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileDetailsScreen(
    navigateToEditProfile: () -> Unit,
    navigateToAccountScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val pagerState = rememberPagerState()
    val imageList = remember { mutableListOf<String>() }
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditProfile() },
                backgroundColor = lightPurple,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Profile")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if (!imageList.contains(state.userDetails?.profileImage?.profileImage1)) {
                state.userDetails?.profileImage?.profileImage1?.let { imageList.add(it) }
            }
            if (!imageList.contains(state.userDetails?.profileImage?.profileImage2)) {
                state.userDetails?.profileImage?.profileImage2?.let { imageList.add(it) }
            }
            if (state.userDetails?.profileImage?.profileImage3 != "" &&
                !imageList.contains(state.userDetails?.profileImage?.profileImage3)
            ) {
                state.userDetails?.profileImage?.profileImage3?.let { imageList.add(it) }
            }
            if (state.userDetails?.profileImage?.profileImage3 != "" &&
                !imageList.contains(state.userDetails?.profileImage?.profileImage4)
            ) {
                state.userDetails?.profileImage?.profileImage4?.let { imageList.add(it) }
            }
            if (state.userDetails?.profileImage?.profileImage3 != "" &&
                !imageList.contains(state.userDetails?.profileImage?.profileImage5)
            ) {
                state.userDetails?.profileImage?.profileImage5?.let { imageList.add(it) }
            }
            if (state.userDetails?.profileImage?.profileImage3 != "" &&
                !imageList.contains(state.userDetails?.profileImage?.profileImage6)
            ) {
                state.userDetails?.profileImage?.profileImage6?.let { imageList.add(it) }
            }

            Box(
                modifier = modifier
                    .weight(1f)
            ) {
                if (state.userDetails != null) {
                    SliderView(pagerState, imageList)
                    DotsIndicator(
                        totalDots = imageList.size - 1,
                        selectedIndex = pagerState.currentPage
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start,
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    ResultText(
                        text = "${state.userDetails?.firstName},${state.userDetails?.years}",
                        color = MaterialTheme.colors.background,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                }
            }

            Column(
                modifier = modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                if (state.userDetails?.userBio?.isNotEmpty() == true) {
                    BasicText(
                        text = AppText.about_me,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                    ResultText(
                        text = state.userDetails?.userBio ?: "No Bio added yet",
                        color = Color.Gray,
                        fontWeight = FontWeight.Light,
                        modifier = modifier.offset(y = (-8).dp)
                    )
                }

                BasicText(
                    text = AppText.my_basics,
                    fontSize = 18.sp
                )
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    InfoItem(text = "${state.userDetails?.gender}")
                    val sexuality =
                        if (state.userDetails?.interestedIn == "Men" &&
                            state.userDetails?.gender == "Male"
                        ) {
                            "Gay"
                        } else if (state.userDetails?.interestedIn == "Women" &&
                            state.userDetails?.gender == "Female"
                        ) {
                            "Gay"
                        } else if (state.userDetails?.interestedIn == "Everyone") {
                            "BiSexual"
                        } else {
                            "Straight"
                        }
                    Spacer(modifier = modifier.width(4.dp))
                    InfoItem(text = sexuality)
                }

                BasicText(
                    text = AppText.interested_in,
                    fontSize = 18.sp
                )

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    InfoItem(text = "${state.userDetails?.interestedIn}")
                }

                BasicText(
                    text = AppText.interests,
                    fontSize = 18.sp
                )
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    InfoItem(text = "Netflix")
                    Spacer(modifier = modifier.width(4.dp))
                    InfoItem(text = "Cars")
                    Spacer(modifier = modifier.width(4.dp))
                    InfoItem(text = "Music")
                    Spacer(modifier = modifier.width(4.dp))
                    InfoItem(text = "AI")
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .textPadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "Location")
                    BasicText(
                        text = AppText.location,
                        fontSize = 18.sp,
                        modifier = modifier.offset(x = (-8).dp)
                    )
                }
                ResultText(
                    text = "${state.userDetails?.locationName}",
                    color = Color.Gray,
                    modifier = modifier
                        .offset(x = (8).dp, y = (-16).dp)
                        .padding(bottom = 70.dp)
                )
            }
        }
    }
}

@Composable
fun InfoItem(text: String) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(lightPurple)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colors.background,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp, 8.dp)
        )
    }
}
