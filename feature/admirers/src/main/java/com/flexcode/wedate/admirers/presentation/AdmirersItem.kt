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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.flexcode.wedate.common.composables.ResultText
import com.flexcode.wedate.common.composables.SwipeRightLeftIcon
import com.flexcode.wedate.common.snackbar.SnackBarManager
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.home.data.model.Likes
import timber.log.Timber

@Composable
fun AdmirerItem(
    like: Likes,
    modifier: Modifier,
    viewModel: AdmirersViewModel
) {
    val state by viewModel.state
    Column {
        Box(
            modifier = modifier
                .padding(10.dp)
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 8.dp, topEnd = 30.dp,
                        bottomStart = 8.dp, bottomEnd = 8.dp
                    )
                )
                .height(200.dp)
        ) {
            Card {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1566753323558-f4e0952af115?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1021&q=80",
                    contentDescription = "Admired by ${like.firstName}",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
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
                        SnackBarManager.showError("You Matched With ${like.firstName}")

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