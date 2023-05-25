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
package com.flexcode.wedate.chatsscreen.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.flexcode.wedate.common.ImageShimmer
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.BannerAdView
import com.flexcode.wedate.common.composables.ResultText
import com.flexcode.wedate.common.composables.SwipeRightLeftIcon
import com.flexcode.wedate.common.theme.deepLightPurple
import com.flexcode.wedate.common.theme.lightPurple
import com.flexcode.wedate.common.theme.onlineGreen
import kotlinx.coroutines.launch

@Composable
fun ChatsScreen(
    modifier: Modifier = Modifier,
    navigateToMatchScreen: () -> Unit,
    data: String?,
    viewModel: ChatScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = state.messages.size) {
        scope.launch {
            if (state.messages.size > 1) {
                scrollState.animateScrollToItem(state.messages.size - 1)
            }
        }
    }

    Scaffold(topBar = {
        TopBar(modifier, navigateToMatchScreen, state)
    }) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(bottom = 125.dp),
                state = scrollState
            ) {
                items(state.messages.size) { i ->
                    val msg = state.messages.sortedBy { it.timeStamp }[i]
                    val currentUid = viewModel.getUid()
                    if (msg.messageSenderId == currentUid) {
                        MessageByMeItem(msg.message)
                    } else {
                        MessageFromCrushItem(msg.message)
                    }
                }
            }

            Column(
                modifier = modifier.align(Alignment.BottomCenter),
            ) {
                CustomTextField(
                    text = state.message,
                    onValueChange = viewModel::onMessageChange,
                    viewModel = viewModel,
                    state = state,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    userId = data!!
                )
                BannerAdView()
            }



        }

        LaunchedEffect(key1 = state.userDetails) {
            viewModel.getUserDetails("$data")
            viewModel.getAllMessages(messagesId = "${viewModel.getUid()}$data")
        }
    }
}

@Composable
fun CustomTextField(
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    viewModel: ChatScreenViewModel,
    state: ChatScreenState,
    userId: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 0.dp,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, lightPurple)
    ) {
        TextField(
            value = text,
            onValueChange = { onValueChange(it) },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.type_msg),
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.Gray
                    ),
                    textAlign = TextAlign.Center
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                CommonIconButton(imageVector = Icons.Default.FileUpload, onClick = {})
            },
            trailingIcon = {
                CommonIconButton(imageVector = Icons.Default.Send, onClick = {
                    viewModel.sendMessage(
                        message = state.message,
                        messageSenderId = viewModel.getUid(),
                        messageTimeStamp = System.currentTimeMillis(),
                        lastMsgTime = System.currentTimeMillis(),
                        matchId = state.userDetails?.id.toString()
                    )
                    viewModel.getAllMessages(messagesId = "${viewModel.getUid()}$userId")
                    // workmanager
                    viewModel.saveChatProfileToCurrentUser(
                        crushUserId = userId,
                        firstName = state.userDetails?.firstName.toString(),
                        profileImage = state.userDetails?.profileImage?.profileImage1.toString(),
                        lastMsgTime = System.currentTimeMillis(),
                        lastMsg = state.message
                    )
                    viewModel.saveChatProfileToCrush(
                        crushUserId = userId,
                        firstName = state.currentUserDetails?.firstName.toString(),
                        profileImage =
                        state.currentUserDetails?.profileImage?.profileImage1.toString(),
                        lastMsgTime = System.currentTimeMillis(),
                        lastMsg = state.message
                    )
                    state.message = ""
                })
            }
        )
    }
}

@Composable
fun CommonIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(43.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = { onClick() }) {
            Icon(
                imageVector = imageVector,
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

@Composable
fun TopBar(modifier: Modifier, navigateToMatchScreen: () -> Unit, state: ChatScreenState) {
    val gradient = Brush.horizontalGradient(
        listOf(lightPurple, deepLightPurple),
        startX = 500.0f,
        endX = 1330.0f
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(brush = gradient),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SwipeRightLeftIcon(
            onClick = { navigateToMatchScreen() },
            icon = Icons.Default.ArrowBackIos,
            contentDesc = "Back",
            height = 20.dp,
            width = 20.dp,
            paddingValues = PaddingValues(start = 10.dp, 0.dp),
            tint = Color.Black
        )
        Spacer(modifier = modifier.width(5.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("${state.userDetails?.profileImage?.profileImage1}")
                .crossfade(true)
                .placeholder(ImageShimmer().shimmerDrawable)
                .build(),
            contentDescription = "Chat with ${state.userDetails?.firstName}",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(CircleShape)
                .size(60.dp)
        )

        Column(
            modifier = modifier.weight(0.8f)
        ) {
            ResultText(
                text = "${state.userDetails?.firstName}",
                color = MaterialTheme.colors.background,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            ResultText(
                text = "", // online/offline
                color = onlineGreen,
                modifier = modifier.offset(y = (-16).dp),
                fontSize = 14.sp
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            TopBarIcons(icon = Icons.Default.VideoCall)
            TopBarIcons(icon = Icons.Default.Call)
            TopBarIcons(icon = Icons.Default.More)
        }
    }
}

@Composable
fun TopBarIcons(icon: ImageVector) {
    SwipeRightLeftIcon(
        onClick = { /**TODO*/ },
        icon = icon,
        contentDesc = "More",
        height = 20.dp,
        width = 20.dp,
        paddingValues = PaddingValues(start = 5.dp, 0.dp),
        tint = Color.Black
    )
}
