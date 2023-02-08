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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.BasicText
import com.flexcode.wedate.common.composables.ExtraScreenText
import com.flexcode.wedate.common.composables.LogoComposableImage
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.common.theme.lightPurple
import com.flexcode.wedate.common.R.string as AppText

@Composable
fun AdmirersScreen(
    //openScreen: (String,) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AdmirersViewModel = hiltViewModel()
) {

    val state by viewModel.state
    val admirers = state.admirers

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
                fontSize = 25.sp, fontWeight = FontWeight.Bold
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
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = deepBrown
                    )
                }else{
                    LazyVerticalGrid(columns = GridCells.Fixed(2)){
                        items(state.admirers.size) { i->
                            val like = state.admirers[i]
                            AdmirerItem(
                                modifier = modifier.clip(
                                    shape = RoundedCornerShape(
                                        topStart = 8.dp,
                                        topEnd = 30.dp,
                                        bottomStart = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                ),
                                like = like
                            )
                        }
                    }
                }
            }
        }

    }

}

@Composable
fun NoResultFoundAnimation() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.noresult))
    LottieAnimation(composition = composition, iterations = 2, modifier = Modifier.size(300.dp), alignment = Alignment.Center)
}