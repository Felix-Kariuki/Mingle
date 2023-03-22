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
package com.flexcode.wedate.matches.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.common.R.drawable as AppIcon
import com.flexcode.wedate.common.R.string as AppText
import com.flexcode.wedate.common.composables.BasicText
import com.flexcode.wedate.common.composables.NoResultFoundAnimation
import com.flexcode.wedate.common.composables.SearchTextField
import com.flexcode.wedate.common.ext.textPadding
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.common.theme.lightPurple
import com.flexcode.wedate.matches.composables.ChatItem
import com.flexcode.wedate.matches.composables.MatchesItem

@Composable
fun MatchesScreen(
    navigateToChats: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatchesViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current

    val state by viewModel.state

    val gradient = Brush.verticalGradient(
        listOf(lightPurple, Color.White),
        startY = 500.0f,
        endY = 1300.0f
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(brush = gradient)
    ) {
        SearchTextField(
            modifier = modifier.textPadding(),
            value = state.searchValue,
            leadingIcon = AppIcon.ic_search,
            placeholder = AppText.search,
            onNewValue = viewModel::onSearchValueChange,
            onSearch = {
                focusManager.clearFocus()
            }
        )

        if (state.matches.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                NoResultFoundAnimation()
                ErrorMessage(text = AppText.no_matches)
            }
        } else if (state.matches.isNotEmpty() && state.chatProfiles.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                BasicText(
                    text = AppText.matches,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                MatchesComposable(state, navigateToChats)
                NoResultFoundAnimation()
                ErrorMessage(text = AppText.no_chats)
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                BasicText(
                    text = AppText.matches,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                MatchesComposable(state, navigateToChats)
                BasicText(text = AppText.chats, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                ChatsComposable(state, navigateToChats)
            }
        }
    }
}

@Composable
fun ChatsComposable(
    state: MatchesState,
    navigateToChats: (String) -> Unit
) {
    LazyColumn(
        Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(state.chatProfiles.size) { chat ->
            val chatProfile = state.chatProfiles[chat]
            ChatItem(chatProfile = chatProfile, navigateToChats)
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
fun MatchesComposable(
    state: MatchesState,
    navigateToChats: (String) -> Unit
) {
    LazyRow(
        Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 6.dp)
    ) {
        items(state.matches.size) { i ->
            val match = state.matches[i]
            MatchesItem(match = match, navigateToChats)
        }
    }
}

@Composable
fun ErrorMessage(
    text: Int
) {
    BasicText(
        text = text,
        fontSize = 15.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Normal,
        color = deepBrown
    )
}
