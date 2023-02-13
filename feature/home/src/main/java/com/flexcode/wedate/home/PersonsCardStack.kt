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

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.common.R.string as AppText
import com.flexcode.wedate.common.composables.BasicText
import com.flexcode.wedate.common.composables.ResultText
import com.flexcode.wedate.common.composables.SwipeRightLeftIcon
import com.flexcode.wedate.common.ext.moveTo
import com.flexcode.wedate.common.ext.visible
import com.flexcode.wedate.common.snackbar.SnackBarManager
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.common.theme.onlineGreen
import com.flexcode.wedate.home.presentation.HomeViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PersonsCardStack(
    modifier: Modifier = Modifier,
    items: List<User>,
    thresholdConfig: (Float, Float) -> ThresholdConfig = { _, _ -> FractionalThreshold(0.2f) },
    velocityThreshold: Dp = 125.dp,
    onSwipeLeft: (person: User) -> Unit = {},
    onSwipeRight: (person: User) -> Unit = {},
    onEmptyStack: (lastItem: User) -> Unit = {},
    viewModel: HomeViewModel
) {
    var i by remember {
        mutableStateOf(items.size - 1)
    }

    if (i == -1) {
        onEmptyStack(items.last())
    }
    val state by viewModel.state

    val cardStackController = rememberCardStackController()

    cardStackController.onSwipeLeft = {
        Timber.i("SWIPEDLEFT:: ${items.asReversed()[i].firstName}")
        // save dislike
        onSwipeLeft(items[i])
        i--
    }
    cardStackController.onSwipeRight = {
        val currentShowingPersonOnStackLikedByDetail = items.asReversed()[i].likedBy
        val currentLoggedInUserId = viewModel.getUid()
        // savelike
        if (items.asReversed()[i].likedBy != null) {
            if (!items.asReversed()[i].likedBy?.contains(viewModel.getUid())!!) {
                viewModel.saveLikeToCrush(
                    crushUserId = items.asReversed()[i].id,
                    firstName = state.userDetails?.firstName.toString(),
                    locationName = state.userDetails?.locationName.toString(),
                    years = state.userDetails?.years.toString(),
                    lat = state.userDetails?.latitude.toString(),
                    long = state.userDetails?.longitude.toString(),
                    profileImage = state.userDetails?.profileImage?.profileImage1.toString(),
                    matched = false
                )
            }
        } else if (items.asReversed()[i].likedBy == null) {
            /**remove no longer necessary**/
            viewModel.saveLikeToCrush(
                crushUserId = items.asReversed()[i].id,
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
        val currentShowingPersonOnStackId = items.asReversed()[i].id
        if (state.userDetails?.likedBy != null &&
            state.userDetails?.likedBy?.contains(items.asReversed()[i].id)!!
        ) {
            SnackBarManager.showError("You Matched With ${items.asReversed()[i].firstName}")
            /*test**/
            viewModel.saveLikeToCrush(
                crushUserId = items.asReversed()[i].id,
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
                crushUserId = items.asReversed()[i].id,
                firstName = state.userDetails?.firstName.toString(),
                locationName = state.userDetails?.locationName.toString(),
                years = state.userDetails?.years.toString(),
                lat = state.userDetails?.latitude.toString(),
                long = state.userDetails?.longitude.toString(),
                profileImage = state.userDetails?.profileImage?.profileImage1.toString()
            )
            viewModel.saveMatchToCurrentUser(
                crushUserId = items.asReversed()[i].id,
                firstName = items.asReversed()[i].firstName,
                locationName = items.asReversed()[i].locationName,
                years = items.asReversed()[i].years,
                lat = items.asReversed()[i].latitude,
                long = items.asReversed()[i].longitude,
                profileImage = items.asReversed()[i].profileImage?.profileImage1.toString()
            )
        }
        onSwipeRight(items[i])
        i--
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 60.dp, top = 10.dp, start = 10.dp, end = 10.dp)
            .clip(
                shape = RoundedCornerShape(
                    topEnd = 130.dp,
                    bottomEnd = 25.dp,
                    topStart = 25.dp,
                    bottomStart = 25.dp
                )
            )
    ) {
        val stack = createRef()

        Box(
            modifier = modifier
                .constrainAs(stack) {
                    top.linkTo(parent.top)
                }
                .draggableStack(
                    controller = cardStackController,
                    thresholdConfig = thresholdConfig,
                    velocityThreshold = velocityThreshold
                )
                .fillMaxHeight()
        ) {
            items.asReversed().forEachIndexed { index, item ->
                PersonCard(
                    modifier = Modifier
                        .moveTo(
                            x = if (index == i) cardStackController.offsetX.value else 0f,
                            y = if (index == i) cardStackController.offsetY.value else 0f
                        )
                        .visible(visible = index == i || index == i - 1)
                        .graphicsLayer(
                            rotationZ = if (index == i) cardStackController.rotation.value else 0f,
                            scaleX = if (index < i) cardStackController.scale.value else 1f,
                            scaleY = if (index < i) cardStackController.scale.value else 1f
                        ),
                    item,
                    cardStackController
                )
            }
        }
    }
}

@Composable
fun PersonCard(
    modifier: Modifier = Modifier,
    person: User,
    cardStackController: CardStackController
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = person.profileImage?.profileImage1,
            contentDescription = person.firstName,
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
                status = if (person.isOnline) AppText.offline else AppText.online,
                backgroundColor = if (person.isOnline) deepBrown else onlineGreen
            )
        }

        Column(
            modifier = modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            ResultText(
                text = "${person.firstName},${person.years}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = modifier.offset(x = (-16).dp, y = (30).dp)
            )

            ResultText(
                text = person.locationName,
                color = Color.White,
                fontSize = 20.sp,
                modifier = modifier.offset(x = ((-16).dp), y = 16.dp)
            )

            Row {
                SwipeRightLeftIcon(
                    onClick = { cardStackController.swipeLeft() },
                    icon = Icons.Default.Close,
                    contentDesc = "Dislike${person.firstName}",
                    paddingValues = PaddingValues(50.dp, 0.dp, 0.dp, 0.dp)
                )
                Spacer(modifier = Modifier.weight(1f))

                SwipeRightLeftIcon(
                    onClick = { cardStackController.swipeRight() },
                    icon = Icons.Default.FavoriteBorder,
                    contentDesc = "Like${person.firstName}"
                )
            }
        }
    }
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
