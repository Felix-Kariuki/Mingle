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
package com.flexcode.wedate.lovecalculator.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.common.R.string as AppText
import com.flexcode.wedate.common.composables.*
import com.flexcode.wedate.common.extestions.basicButton
import com.flexcode.wedate.common.extestions.fieldModifier
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.common.theme.lightPurple

@Composable
fun LoveCalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: LoveCalculatorViewModel = hiltViewModel(),
    openAndPopUp: () -> Unit
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
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                SwipeRightLeftIcon(
                    onClick = { openAndPopUp() },
                    icon = Icons.Default.ArrowBackIos,
                    contentDesc = "Back",
                    height = 30.dp,
                    width = 30.dp,
                    paddingValues = PaddingValues(0.dp),
                    tint = Color.Black
                )
                AppTitleText(
                    fontSize = 23.sp,
                    modifier = modifier
                        .fillMaxWidth()
                        .offset(y = -10.dp),
                    text = AppText.calculator
                )
            }

            BasicText(text = AppText.calculator_desc, color = MaterialTheme.colors.background)

            BasicText(text = AppText.your_name)
            BasicField(
                value = state.yourName,
                onNewValue = viewModel::onYourNameChange,
                text = AppText.your_name,
                modifier = modifier.fieldModifier()
            )

            BasicText(text = AppText.your_crush_name)
            BasicField(
                value = state.crushName,
                onNewValue = viewModel::onCrushNameChange,
                text = AppText.your_crush_name,
                modifier = modifier.fieldModifier()
            )

            Spacer(modifier = modifier.height(10.dp))
            LovePercentageCircularIndicator(
                percentage = (state.calculatorResponse?.percentage?.toFloat() ?: 0f),
                modifier = modifier
                    .fillMaxWidth(0.17f)
            )

            ResultText(
                text = state.calculatorResponse?.result ?: "",
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                color = deepBrown
            )

            BasicButton(
                text = AppText.calculate,
                modifier = modifier
                    .basicButton()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                viewModel.getLovePercentage()
            }
        }
    }
}

@Composable
fun LovePercentageCircularIndicator(
    modifier: Modifier = Modifier,
    percentage: Float,
    number: Int = 1,
    fontSize: TextUnit = 38.sp,
    radius: Dp = 90.dp,
    color: Color = deepBrown,
    strokeWidth: Dp = 3.dp,
    animationDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(
            modifier = Modifier
                .size(radius * 2f)
        ) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = (360 * (currentPercentage.value * 0.1)).toFloat(),
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = "${(currentPercentage.value * number).toInt()}%",
            color = deepBrown,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}
