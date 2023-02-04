package com.flexcode.wedate.common.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Column(modifier = Modifier.size(100.dp)) {
            val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loveloading))
            LottieAnimation(composition = composition)
        }
    }

}