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
package com.flexcode.wedate.admirers.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.flexcode.wedate.common.ImageShimmer
import com.flexcode.wedate.common.composables.ResultText
import com.flexcode.wedate.common.composables.SwipeRightLeftIcon
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.home.MatchNotificationService
import com.flexcode.wedatecompose.network.data.models.home.Likes
import timber.log.Timber

@Composable
fun AdmirerItem(
    like: Likes,
    modifier: Modifier,
    viewModel: AdmirersViewModel
) {
    val state by viewModel.state
    val context = LocalContext.current
    Column {
        Box(
            modifier = modifier
                .padding(10.dp)
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 30.dp,
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    )
                )
                .height(200.dp)
                .blur(30.dp)
        ) {
            Card {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(like.profileImage)
                        .crossfade(true)
                        .placeholder(ImageShimmer().shimmerDrawable)
                        .build(),
                    contentDescription = "Admired by ${like.firstName}",
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = modifier
                    .align(Alignment.BottomStart)
            ) {
                ResultText(
                    text = "${like.firstName},${like.years}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SwipeRightLeftIcon(
                onClick = { /*TODO*/ },
                icon = Icons.Default.Close,
                contentDesc = "Dislike ${like.firstName}",
                tint = Color.Black,
                paddingValues = PaddingValues(0.dp),
                height = 30.dp,
                width = 30.dp
            )

            SwipeRightLeftIcon(
                onClick = {
                    Timber.i("LIKED:: ${like.id}")
                    viewModel.saveLikeToCrush(
                        crushUserId = like.id,
                        firstName = state.userDetails?.firstName.toString(),
                        locationName = state.userDetails?.locationName.toString(),
                        years = state.userDetails?.years.toString(),
                        lat = state.userDetails?.latitude.toString(),
                        long = state.userDetails?.longitude.toString(),
                        profileImage = state.userDetails?.profileImage?.profileImage1.toString(),
                        matched = true

                    )
                    if (state.userDetails?.likedBy != null && state.userDetails?.likedBy!!.contains(
                            like.id
                        )
                    ) {
                        viewModel.getAllLikedBy(viewModel.getUid())
                        val service = MatchNotificationService(context.applicationContext)
                        service.showNotification(like.firstName)

                        viewModel.saveMatchToCrush(
                            crushUserId = like.id,
                            firstName = state.userDetails?.firstName.toString(),
                            locationName = state.userDetails?.locationName.toString(),
                            years = state.userDetails?.years.toString(),
                            lat = state.userDetails?.latitude.toString(),
                            long = state.userDetails?.longitude.toString(),
                            profileImage = state.userDetails?.profileImage?.profileImage1.toString()
                        )
                        viewModel.saveMatchToCurrentUser(
                            crushUserId = like.id,
                            firstName = like.firstName,
                            locationName = like.locationName,
                            years = like.years,
                            lat = like.lat,
                            long = like.long,
                            profileImage = like.profileImage
                        )
                    }
                },
                icon = Icons.Default.FavoriteBorder,
                contentDesc = "Like ${like.firstName}",
                tint = deepBrown,
                paddingValues = PaddingValues(0.dp),
                height = 30.dp,
                width = 30.dp
            )
        }
    }
}
