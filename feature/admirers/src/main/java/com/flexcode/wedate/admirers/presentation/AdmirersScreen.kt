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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.R.string as AppText
import com.flexcode.wedate.common.composables.BasicButton
import com.flexcode.wedate.common.composables.BasicText
import com.flexcode.wedate.common.composables.ExtraScreenText
import com.flexcode.wedate.common.composables.LogoComposableImage
import com.flexcode.wedate.common.composables.NoResultFoundAnimation
import com.flexcode.wedate.common.extestions.basicButton
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.common.theme.lightPurple

@Composable
fun AdmirersScreen(
    // openScreen: (String,) -> Unit,
    navigateToAdmirersMaps: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AdmirersViewModel = hiltViewModel()
) {
    val state by viewModel.state

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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Spacer(modifier = modifier.padding(top = 20.dp))

            LogoComposableImage()

            ExtraScreenText(
                text = AppText.admirers,
                color = MaterialTheme.colors.background,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            BasicText(text = AppText.admirers_text, color = MaterialTheme.colors.background)

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.admirers.isEmpty()) {
                    NoResultFoundAnimation()
                    BasicText(
                        text = AppText.no_admirers,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        color = deepBrown
                    )
                } else {
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(state.admirers.size) { i ->
                            val like = state.admirers[i]
                            if (!like.matched) {
                                AdmirerItem(
                                    modifier = modifier.clip(
                                        shape = RoundedCornerShape(
                                            topStart = 8.dp,
                                            topEnd = 30.dp,
                                            bottomStart = 8.dp,
                                            bottomEnd = 8.dp
                                        )
                                    ),
                                    like = like,
                                    viewModel = viewModel
                                )
                            }
                        }
                    }
                }
            }
        }

        Row(modifier = modifier.padding(bottom = 60.dp).align(Alignment.BottomCenter)) {
            BasicButton(
                text = R.string.view_on_map,
                modifier = modifier
                    .basicButton()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                navigateToAdmirersMaps()
            }
        }
    }
}
