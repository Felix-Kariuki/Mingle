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

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.BasicButton
import com.flexcode.wedate.common.composables.BasicText
import com.flexcode.wedate.common.composables.ResultText
import com.flexcode.wedate.common.composables.SwipeRightLeftIcon
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.common.theme.onlineGreen
import com.flexcode.wedate.common.theme.purpleGrey
import com.flexcode.wedate.home.presentation.HomeUiState
import com.flexcode.wedate.home.presentation.HomeViewModel
import com.github.theapache64.twyper.Twyper
import com.github.theapache64.twyper.rememberTwyperController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TwyperScreen(
    items: MutableList<User>,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    context: Context
) {
    val state by viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp, top = 10.dp, start = 10.dp, end = 10.dp)
            .clip(
                shape = RoundedCornerShape(
                    topEnd = 130.dp,
                    bottomEnd = 25.dp,
                    topStart = 25.dp,
                    bottomStart = 25.dp
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val twyperController = rememberTwyperController()
        Twyper(
            items = items,
            twyperController = twyperController,
            onItemRemoved = { item, direction ->
                if (direction.toString() == "RIGHT") {
                    onLikePerson(viewModel, item, items, state, context)
                } else {
                    onDislikePerson(viewModel, item, items, state, context)
                    items.remove(item)
                }
            },
            onEmpty = {
                println("End reached")
            }
        ) { item ->

            val modalBottomSheetState =
                rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
            val scope = rememberCoroutineScope()
            ModalBottomSheetLayout(
                sheetContent = {
                    BottomSheetContent(modifier = modifier, item)
                },
                sheetState = modalBottomSheetState,
                sheetShape = RoundedCornerShape(
                    topEnd = 25.dp,
                    bottomEnd = 25.dp,
                    topStart = 25.dp,
                    bottomStart = 25.dp
                ),
                sheetBackgroundColor = Color.White

            ) {
                Box(
                    modifier = modifier.clip(
                        shape = RoundedCornerShape(
                            topEnd = 130.dp,
                            bottomEnd = 25.dp,
                            topStart = 25.dp,
                            bottomStart = 25.dp
                        )
                    )
                ) {
                    AsyncImage(
                        model = item.profileImage?.profileImage1,
                        contentDescription = item.firstName,
                        contentScale = ContentScale.Crop,
                        modifier = modifier.fillMaxSize()
                    )
                    Row(
                        modifier = modifier
                            .wrapContentWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        StatusItem(
                            status = if (item.online == false) {
                                R.string.offline
                            } else {
                                R.string.online
                            },
                            backgroundColor = if (item.online == false) {
                                deepBrown
                            } else {
                                onlineGreen
                            }
                        )
                    }
                    Column(
                        modifier = modifier
                            .align(Alignment.BottomStart)
                            .padding(20.dp)
                    ) {
                        ResultText(
                            text = "${item.firstName},${item.years}",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            modifier = modifier.offset(x = (-16).dp, y = (30).dp)
                        )

                        ResultText(
                            text = item.locationName,
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = modifier.offset(x = ((-16).dp), y = 16.dp)
                        )

                        Row {
                            SwipeRightLeftIcon(
                                onClick = {
                                    twyperController.swipeLeft()
                                    onDislikePerson(viewModel, item, items, state, context)
                                },
                                icon = Icons.Default.Close,
                                contentDesc = "Dislike${item.firstName}",
                                paddingValues = PaddingValues(50.dp, 0.dp, 0.dp, 0.dp),
                                circleColor = deepBrown
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            BasicButton(
                                text = R.string.more,
                                modifier = modifier,
                                backGroundColor = purpleGrey
                            ) {
                                scope.launch {
                                    modalBottomSheetState.show()
                                }
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            SwipeRightLeftIcon(
                                onClick = {
                                    twyperController.swipeRight()
                                    onLikePerson(viewModel, item, items, state, context)
                                },
                                icon = Icons.Default.FavoriteBorder,
                                contentDesc = "Like${item.firstName}"
                            )
                        }
                    }
                }
            }
        }
    }
}

fun onDislikePerson(
    viewModel: HomeViewModel,
    item: User,
    items: MutableList<User>,
    state: HomeUiState,
    context: Context
) {
    items.remove(item)
}

fun onLikePerson(
    viewModel: HomeViewModel,
    item: User,
    items: MutableList<User>,
    state: HomeUiState,
    context: Context
) {
    val currentShowingPersonOnStackLikedByDetail = item.likedBy
    val currentLoggedInUserId = viewModel.getUid()
    // savelike
    if (item.likedBy != null) {
        if (!item.likedBy?.contains(viewModel.getUid())!!) {
            viewModel.saveLikeToCrush(
                crushUserId = item.id,
                firstName = state.userDetails?.firstName.toString(),
                locationName = state.userDetails?.locationName.toString(),
                years = state.userDetails?.years.toString(),
                lat = state.userDetails?.latitude.toString(),
                long = state.userDetails?.longitude.toString(),
                profileImage = state.userDetails?.profileImage?.profileImage1.toString(),
                matched = false
            )
        }
    } else if (item.likedBy == null) {
        /**remove no longer necessary**/
        viewModel.saveLikeToCrush(
            crushUserId = item.id,
            firstName = state.userDetails?.firstName.toString(),
            locationName = state.userDetails?.locationName.toString(),
            years = state.userDetails?.years.toString(),
            lat = state.userDetails?.latitude.toString(),
            long = state.userDetails?.longitude.toString(),
            profileImage = state.userDetails?.profileImage?.profileImage1.toString(),
            matched = false
        )
    }

    // check match
    val currentLoggedInUserLikedByDetail = state.userDetails?.likedBy
    val currentShowingPersonOnStackId = item.id
    if (state.userDetails?.likedBy != null &&
        state.userDetails?.likedBy?.contains(item.id)!!
    ) {
        // SnackBarManager.showError("You Matched With ${items.asReversed()[i].firstName}")
        val service = MatchNotificationService(context.applicationContext)
        service.showNotification(item.firstName)
        /*test**/
        viewModel.saveLikeToCrush(
            crushUserId = item.id,
            firstName = state.userDetails?.firstName.toString(),
            locationName = state.userDetails?.locationName.toString(),
            years = state.userDetails?.years.toString(),
            lat = state.userDetails?.latitude.toString(),
            long = state.userDetails?.longitude.toString(),
            profileImage = state.userDetails?.profileImage?.profileImage1.toString(),
            matched = true
        )
        // save match
        viewModel.saveMatchToCrush(
            crushUserId = item.id,
            firstName = state.userDetails?.firstName.toString(),
            locationName = state.userDetails?.locationName.toString(),
            years = state.userDetails?.years.toString(),
            lat = state.userDetails?.latitude.toString(),
            long = state.userDetails?.longitude.toString(),
            profileImage = state.userDetails?.profileImage?.profileImage1.toString()
        )
        viewModel.saveMatchToCurrentUser(
            crushUserId = item.id,
            firstName = item.firstName,
            locationName = item.locationName,
            years = item.years,
            lat = item.latitude,
            long = item.longitude,
            profileImage = item.profileImage?.profileImage1.toString()
        )
        // delete on dislike
        viewModel.deleteLikedByFromMe(
            userLikeId = item.id
        )
    }
    items.remove(item)
}

@Composable
fun StatusItem(
    modifier: Modifier = Modifier,
    status: Int,
    backgroundColor: Color
) {
    Card(
        modifier = modifier
            .width(100.dp)
            .height(35.dp),
        shape = RoundedCornerShape(50.dp),
        elevation = 10.dp,
        backgroundColor = backgroundColor
    ) {
        BasicText(text = status, color = Color.White, fontWeight = FontWeight.SemiBold)
    }
}
