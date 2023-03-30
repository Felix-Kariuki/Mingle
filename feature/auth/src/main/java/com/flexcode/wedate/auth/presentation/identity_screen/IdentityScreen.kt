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
package com.flexcode.wedate.auth.presentation.identity_screen

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
import com.flexcode.wedate.common.R.string as AppText
import com.flexcode.wedate.common.composables.*
import com.flexcode.wedate.common.extestions.basicButton
import com.flexcode.wedate.common.extestions.fieldModifier

@Composable
fun IdentityScreen(
    modifier: Modifier = Modifier,
    openScreen: (String) -> Unit,
    viewModel: IdentifyScreenViewModel = hiltViewModel()
) {
    val user by viewModel.user
    val state by viewModel.state

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenTitlesText(
            text = AppText.identify,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )

        ExtraScreenText(text = AppText.gender)
        GenderSelector(
            onSearchOptionClick = { option ->
                viewModel.setSelectedGenderOption(option)
            },
            isSelected = { option ->
                viewModel.selectedGenderOption.value == option
            }
        )
        ExtraScreenText(text = AppText.dob, modifier = modifier.offset(y = (-40).dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .offset(y = (-40).dp)
                .fieldModifier()
        ) {
            BasicField(
                text = AppText.dd,
                value = state.dd,
                onNewValue = viewModel::onDateOfBirthChange
            )
            Spacer(modifier = modifier.width(20.dp))
            BasicField(
                text = AppText.mm,
                value = state.mm,
                onNewValue = viewModel::onMonthOfBirthChange
            )
            Spacer(modifier = modifier.width(20.dp))

            BasicField(
                text = AppText.yy,
                value = state.yy,
                onNewValue = viewModel::onYearOfBirthChange
            )
        }

        Row(
            modifier = modifier
                .weight(1f)
                .padding(bottom = 30.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            BasicButton(
                text = AppText.next,
                modifier = modifier
                    .basicButton()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                viewModel.onContinueClicked(openScreen)
                // viewModel.saveYearOfBirth()
            }
        }
    }
}

@Composable
fun GenderSelector(
    onSearchOptionClick: (String) -> Unit,
    isSelected: (String) -> Boolean
) {
    GenderOptionsComponent(
        options = genderOptions,
        onClick = onSearchOptionClick,
        isSelected = isSelected
    )
}

private val genderOptions = listOf("Male", "Female")

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
