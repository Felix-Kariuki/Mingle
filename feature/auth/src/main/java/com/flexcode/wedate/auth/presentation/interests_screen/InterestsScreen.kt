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
package com.flexcode.wedate.auth.presentation.interests_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.BasicButton
import com.flexcode.wedate.common.composables.ScreenTitlesText
import com.flexcode.wedate.common.composables.SelectOption
import com.flexcode.wedate.common.ext.basicButton

@Composable
fun InterestsScreen(
    modifier: Modifier = Modifier,
    openScreen: (String) -> Unit,
    viewModel: InterestsViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenTitlesText(
            text = R.string.interest,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )

        InterestsSelector(
            onSearchOptionClick = { option ->
                viewModel.setSelectedInterestsOption(option)
            },
            isSelected = {
                viewModel.selectedInterestsOption.value == it
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
                viewModel.onContinueClicked(openScreen)
            }
        }
    }
}

@Composable
fun InterestsSelector(
    onSearchOptionClick: (String) -> Unit,
    isSelected: (String) -> Boolean
) {
    GenderOptionsComponent(
        options = genderOptions,
        onClick = onSearchOptionClick,
        isSelected = isSelected
    )
}

private val genderOptions = listOf("Women", "Men", "Everyone")

@Composable
fun GenderOptionsComponent(
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
