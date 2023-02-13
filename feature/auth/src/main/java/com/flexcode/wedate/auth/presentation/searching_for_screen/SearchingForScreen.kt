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
package com.flexcode.wedate.auth.presentation.searching_for_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.BasicButton
import com.flexcode.wedate.common.composables.LoadingAnimation
import com.flexcode.wedate.common.composables.ScreenTitlesText
import com.flexcode.wedate.common.composables.SelectOption
import com.flexcode.wedate.common.ext.basicButton

@Composable
fun SearchingForScreen(
    modifier: Modifier = Modifier,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SearchingForViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val visible: String = if (state.isLoading == "true") {
        "true"
    } else {
        "false"
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenTitlesText(
                text = R.string.searching_for,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )

            SearchingForSelector(
                onSearchOptionClick = { option ->
                    viewModel.setSelectSearchForOption(option)
                },
                isSelected = { option ->
                    viewModel.selectSearchForOption.value == option
                }
            )

            Row(
                modifier = modifier
                    .weight(1f)
                    .padding(bottom = 30.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                BasicButton(
                    text = R.string.next,
                    modifier = modifier
                        .basicButton()
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    viewModel.registerUser(openAndPopUp)
                }
            }
        }
        if (visible == "true") {
            LoadingAnimation()
        }
    }
}

@Composable
fun SearchingForSelector(
    onSearchOptionClick: (String) -> Unit,
    isSelected: (String) -> Boolean
) {
    SearchOptionsComponent(
        options = searchOptions,
        onClick = onSearchOptionClick,
        isSelected = isSelected
    )
}

private val searchOptions = listOf("Relationship", "Hookup", "Something casual", "Don't know yet")

@Composable
fun SearchOptionsComponent(
    options: List<String>,
    onClick: (String) -> Unit,
    isSelected: (String) -> Boolean
) {
    LazyColumn {
        items(options) { option ->
            SelectOption(
                option = option,
                onClick = onClick,
                isSelected = isSelected
            )
        }
    }
}
