package com.flexcode.wedatecompose.presentation.splash_screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedatecompose.R
import com.flexcode.wedatecompose.common.utils.Constants
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.showError.value){
            Text(text = stringResource(R.string.generic_error))
            /*BasicButton(text = R.string.try_again, modifier = Modifier.basicButton()) {
                viewModel.onAppStart(openAndPopUp)
            }*/
        }else{
            LottieAnimationLove()
        }
    }

    LaunchedEffect(key1 = true ){
        delay(Constants.SPLASH_TIMEOUT)
        viewModel.onAppStart(openAndPopUp)
    }
}

@Composable
fun LottieAnimationLove() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loveanimation))
    LottieAnimation(composition = composition, iterations = 2)
}