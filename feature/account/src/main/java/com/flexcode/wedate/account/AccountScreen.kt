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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.flexcode.inapppurchasescompose.SubscriptionsHelper
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.R.drawable as AppIcon
import com.flexcode.wedate.common.R.string as AppText
import com.flexcode.wedate.common.composables.*
import com.flexcode.wedate.common.extestions.basicButton
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.common.theme.lightPurple
import com.flexcode.wedate.common.theme.purple
import com.flexcode.wedate.common.utils.Constants

@Composable
fun AccountScreen(
    openScreen: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToProfileDetails: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state

    val billingPurchaseHelper = SubscriptionsHelper(
        LocalContext.current,
        Constants.LOVE_CALCULATOR
    )
    billingPurchaseHelper.setUpBillingPurchases()
    val purchaseDone by billingPurchaseHelper.purchaseDone.collectAsState(false)

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
                    modifier = modifier.offset(x = (16).dp, y = (-10).dp)
                ) {
                    AppTitleText(fontWeight = FontWeight.Normal, fontSize = 20.sp)
                }
                SwipeRightLeftIcon(
                    onClick = { navigateToSettings() },
                    icon = Icons.Default.Settings,
                    contentDesc = "Settings",
                    height = 30.dp,
                    width = 30.dp,
                    paddingValues = PaddingValues(0.dp, end = 10.dp)
                )
            }

            ProfileImage(state, navigateToProfileDetails)
            ResultText(
                text = "${state.userDetails?.firstName},${state.userDetails?.years}",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.background,
                fontSize = 20.sp,
                modifier = modifier.offset(y = (-8).dp)
            )

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
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
                    if (purchaseDone) {
                        billingPurchaseHelper.initializePurchase()
                    } else {
                        // save purchase if done
                        openScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun SubscriptionsCard(
    text: Int,
    icon: Int
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
                text = text,
                fontSize = 14.sp,
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
fun ProfileImage(
    state: AccountState,
    navigateToProfileDetails: () -> Unit
) {
    Box(
        modifier = Modifier.size(115.dp)
    ) {
        Card(
            modifier = Modifier
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
                .clickable {
                    navigateToProfileDetails()
                },
            backgroundColor = purple
        ) {
            Image(
                modifier = Modifier.size(40.dp).padding(8.dp),
                imageVector = Icons.Default.Edit,
                contentDescription = "edit profile"
            )
        }
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(
                        data = state.userDetails?.profileImage?.profileImage1
                    )
                    .placeholder(AppIcon.logo)
                    .build()
            ),
            contentDescription = "profile image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(1.dp, deepBrown, CircleShape).clickable {
                    navigateToProfileDetails()
                },
            contentScale = ContentScale.Crop
        )
    }
}
