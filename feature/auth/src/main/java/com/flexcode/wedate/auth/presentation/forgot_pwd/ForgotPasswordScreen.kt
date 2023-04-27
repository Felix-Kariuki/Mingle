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
package com.flexcode.wedate.auth.presentation.forgot_pwd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.auth.presentation.login.LoginViewModel
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.BasicButton
import com.flexcode.wedate.common.composables.BasicText
import com.flexcode.wedate.common.composables.EmailField
import com.flexcode.wedate.common.composables.LoadingAnimation
import com.flexcode.wedate.common.composables.LogoComposable
import com.flexcode.wedate.common.extestions.basicButton
import com.flexcode.wedate.common.extestions.fieldModifier
import com.flexcode.wedate.common.theme.lightPurple

@Composable
fun ForgotPasswordScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

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

            LogoComposable()

            Spacer(modifier = modifier.padding(top = 50.dp))

            Column(
                modifier = modifier
                    .background(Color.White, shape = RoundedCornerShape(30.dp))
                    .width(370.dp)
                    .height(500.dp)
            ) {
                BasicText(text = R.string.email)
                EmailField(
                    value = uiState.email,
                    icon = Icons.Default.Email,
                    onNewValue = viewModel::onEmailChange,
                    Modifier.fieldModifier()
                )

                BasicButton(
                    text = R.string.forgotPassword,
                    modifier = modifier
                        .basicButton()
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    viewModel.onForgotPasswordClick(openAndPopUp)
                }
            }
        }

        if (uiState.isLoading) {
            LoadingAnimation()
        }
    }
}
