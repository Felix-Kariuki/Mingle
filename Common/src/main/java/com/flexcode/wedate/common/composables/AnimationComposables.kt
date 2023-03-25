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
package com.flexcode.wedate.common.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.flexcode.wedate.common.R

@Composable
fun LoadingAnimation() {
    Column(modifier = Modifier.size(100.dp)) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.loveloading)
        )
        LottieAnimation(composition = composition)
    }
}

@Composable
fun LottieAnimationLove() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.loveanimation)
    )
    LottieAnimation(composition = composition, iterations = 2)
}

@Composable
fun NoResultFoundAnimation() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.noresult)
    )
    LottieAnimation(
        composition = composition,
        iterations = 2,
        modifier = Modifier.size(300.dp),
        alignment = Alignment.Center
    )
}

@Composable
fun SearchingPotentialMatches() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.searching_people)
    )
    LottieAnimation(
        composition = composition,
        iterations = 200,
        modifier = Modifier.size(300.dp),
        alignment = Alignment.Center
    )
}
