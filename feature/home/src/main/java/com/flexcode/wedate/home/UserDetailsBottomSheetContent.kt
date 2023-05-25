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
package com.flexcode.wedate.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.BasicText
import com.flexcode.wedate.common.composables.DotsIndicator
import com.flexcode.wedate.common.composables.ResultText
import com.flexcode.wedate.common.composables.SliderView
import com.flexcode.wedate.common.extestions.textPadding
import com.flexcode.wedate.common.theme.WedateComposeTheme
import com.flexcode.wedate.common.theme.lightPurple
import com.flexcode.wedatecompose.network.data.models.auth.User
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BottomSheetContent(
    modifier: Modifier,
    person: User?
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState()
    val imageList = remember { mutableListOf<String>() }
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(Color.White)
            .verticalScroll(scrollState)

    ) {
        if (!imageList.contains(person?.profileImage?.profileImage1)) {
            person?.profileImage?.profileImage1?.let { imageList.add(it) }
        }
        if (!imageList.contains(person?.profileImage?.profileImage2)) {
            person?.profileImage?.profileImage2?.let { imageList.add(it) }
        }
        if (person?.profileImage?.profileImage3 != "" &&
            !imageList.contains(person?.profileImage?.profileImage3)
        ) {
            person?.profileImage?.profileImage3?.let { imageList.add(it) }
        }
        if (person?.profileImage?.profileImage3 != "" &&
            !imageList.contains(person?.profileImage?.profileImage4)
        ) {
            person?.profileImage?.profileImage4?.let { imageList.add(it) }
        }
        if (person?.profileImage?.profileImage3 != "" &&
            !imageList.contains(person?.profileImage?.profileImage5)
        ) {
            person?.profileImage?.profileImage5?.let { imageList.add(it) }
        }
        if (person?.profileImage?.profileImage3 != "" &&
            !imageList.contains(person?.profileImage?.profileImage6)
        ) {
            person?.profileImage?.profileImage6?.let { imageList.add(it) }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = lightPurple),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    imageVector = Icons.Default.KeyboardDoubleArrowUp,
                    contentDescription = "Swipe down",
                    alpha = .6f,
                    modifier = modifier.padding(8.dp)
                )
            }
            Box(
                modifier = modifier
                    .fillMaxHeight(.4f)
                    .fillMaxHeight()
            ) {
                SliderView(pagerState = pagerState, imageList = imageList)
                DotsIndicator(
                    totalDots = imageList.size - 1,
                    selectedIndex = pagerState.currentPage
                )

                Column(
                    modifier = modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                ) {
                    ResultText(
                        text = "${person?.firstName},${person?.years}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        modifier = modifier.offset(x = (-16).dp, y = (30).dp)
                    )

                    ResultText(
                        text = person?.locationName ?: "Unknown",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = modifier.offset(x = ((-16).dp), y = 16.dp)
                    )

                    Row {
//                        SwipeRightLeftIcon(
//                            onClick = { //todo
//                            },
//                            icon = Icons.Default.Close,
//                            contentDesc = "Dislike${person?.firstName}",
//                            paddingValues = PaddingValues(50.dp, 0.dp, 0.dp, 0.dp)
//                        )
//                        Spacer(modifier = Modifier.weight(1f))
//                        BasicButton(
//                            text = R.string.back,
//                            modifier = modifier,
//                        ) {
//
//                        }
//                        Spacer(modifier = Modifier.weight(1f))
//                        SwipeRightLeftIcon(
//                            onClick = {
//                                //todo
//                            },
//                            icon = Icons.Default.FavoriteBorder,
//                            contentDesc = "Like${person?.firstName}"
//                        )
                    }
                }
            }

            Column(
                modifier = modifier
            ) {
                if (person?.userBio?.isNotEmpty() == true) {
                    BasicText(
                        text = R.string.about_me,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                    ResultText(
                        text = person?.userBio ?: "No Bio added yet",
                        color = Color.Gray,
                        fontWeight = FontWeight.Light,
                        modifier = modifier.offset(y = (-8).dp)
                    )
                }

                BasicText(
                    text = R.string.my_basics,
                    fontSize = 18.sp
                )
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    InfoItem(text = "${person?.gender}")
                    val sexuality =
                        if (person?.interestedIn == "Men" &&
                            person.gender == "Male"
                        ) {
                            "Gay"
                        } else if (person?.interestedIn == "Women" &&
                            person.gender == "Female"
                        ) {
                            "Gay"
                        } else if (person?.interestedIn == "Everyone") {
                            "BiSexual"
                        } else {
                            "Straight"
                        }
                    Spacer(modifier = modifier.width(4.dp))
                    InfoItem(text = sexuality)
                }

                BasicText(
                    text = R.string.interested_in,
                    fontSize = 18.sp
                )

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    InfoItem(text = "${person?.interestedIn}")
                }

                BasicText(
                    text = R.string.interested_in_search_for,
                    fontSize = 18.sp
                )

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    InfoItem(text = "${person?.searchingFor}")
                }

                BasicText(
                    text = R.string.interests,
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
                        text = R.string.location,
                        fontSize = 18.sp,
                        modifier = modifier.offset(x = (-8).dp)
                    )
                }
                ResultText(
                    text = "${person?.locationName}",
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

@Preview(showBackground = true)
@Composable
fun BottomSheetContentPreview() {
    WedateComposeTheme {
        BottomSheetContent(modifier = Modifier, person = User())
    }
}
