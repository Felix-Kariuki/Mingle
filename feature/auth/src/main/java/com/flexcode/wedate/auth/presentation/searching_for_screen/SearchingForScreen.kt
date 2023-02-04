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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.BasicButton
import com.flexcode.wedate.common.composables.ScreenTitlesText
import com.flexcode.wedate.common.composables.SelectOption
import com.flexcode.wedate.common.ext.basicButton

@Composable
fun SearchingForScreen(
    modifier: Modifier = Modifier,
    openAndPopUp: (String,String) -> Unit,
    viewModel: SearchingForViewModel = hiltViewModel()
) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ScreenTitlesText(
            text = R.string.searching_for,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )

        SearchingForSelector(
            onSearchOptionClick = {option ->
                viewModel.setSelectSearchForOption(option)
            },
            isSelected = {option->
                viewModel.selectSearchForOption.value == option
            })


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

}

@Composable
fun SearchingForSelector(
    onSearchOptionClick: (String) -> Unit,
    isSelected: (String) -> Boolean,
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



