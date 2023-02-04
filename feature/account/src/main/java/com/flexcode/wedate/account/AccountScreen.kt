package com.flexcode.wedate.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.*
import com.flexcode.wedate.common.ext.basicButton
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.common.theme.lightPurple
import timber.log.Timber
import com.flexcode.wedate.common.R.drawable as AppIcon
import com.flexcode.wedate.common.R.string as AppText

@Composable
fun AccountScreen(
    openScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountScreenViewModel = hiltViewModel(),
) {

    val state by viewModel.state

    LaunchedEffect(Unit) { viewModel.getUserDetails() }

    val gradient = Brush.verticalGradient(
        listOf(lightPurple, Color.White),
        startY = 500.0f,
        endY = 1300.0f
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = modifier.offset(x = (16).dp, y = (-10).dp),
                ) {
                    AppTitleText(fontWeight = FontWeight.Normal, fontSize = 20.sp)

                }
                Image(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = AppIcon.ic_settings),
                    contentDescription = "Settings"
                )

            }

            ProfileImage()
            ResultText(
                text = "${state.userDetails?.firstName},${state.userDetails?.years}",
                textAlign = TextAlign.Center, fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.background,
                fontSize = 20.sp
            )

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {

                UserInfoItemComposable(
                    text = state.userDetails?.datingStatus.toString(),
                    icon = AppIcon.ic_favourite_border
                )
                UserInfoItemComposable(
                    text = state.userDetails?.gender.toString(),
                    icon = AppIcon.ic_male
                )

            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                SubscriptionsCard(text = AppText.unlock_distance, icon = AppIcon.ic_location)
                SubscriptionsCard(text = AppText.unlock_admirer, icon = AppIcon.ic_favourite_border)
                SubscriptionsCard(text = AppText.subscriptions, icon = AppIcon.logo)
            }

            Row(
                modifier = modifier
                    .weight(1f)
                    .padding(bottom = 60.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                BasicButton(
                    text = R.string.calculator,
                    modifier = modifier
                        .basicButton()
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    openScreen()
                }
            }
        }
    }
}

@Composable
fun SubscriptionsCard(
    text: Int,
    icon: Int,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(118.dp)
            .height(135.dp)
    ) {
        Column {

            Image(
                modifier = Modifier
                    .padding(10.dp)
                    .align(CenterHorizontally)
                    .size(30.dp),
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = text)
            )

            Spacer(modifier = Modifier.weight(1f))
            BasicText(
                text = text, fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun UserInfoItemComposable(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int
) {
    Column(
        modifier = modifier.size(150.dp)
    ) {
        Card(
            shape = CircleShape,
            elevation = 10.dp,
            modifier = modifier.align(CenterHorizontally)
        ) {
            Image(
                modifier = modifier.size(40.dp),
                painter = painterResource(id = icon),
                contentDescription = text
            )
        }
        ResultText(text = text, textAlign = TextAlign.Center)
    }
}

@Composable
fun ProfileImage() {
    Box(
        modifier = Modifier.clip(shape = CircleShape)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = "https://images.unsplash.com/photo-1566753323558-f4e0952af115?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1021&q=80")
                    .placeholder(AppIcon.logo)
                    .build()
            ),
            contentDescription = null,
            modifier = Modifier
                .clickable {

                }
                .size(100.dp)
                .clip(CircleShape)
                .border(1.dp, deepBrown, CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}
