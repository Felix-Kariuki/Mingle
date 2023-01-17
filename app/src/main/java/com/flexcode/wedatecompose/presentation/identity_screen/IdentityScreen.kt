package com.flexcode.wedatecompose.presentation.identity_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedatecompose.common.composables.*
import com.flexcode.wedatecompose.common.ext.basicButton
import com.flexcode.wedatecompose.common.ext.fieldModifier
import com.flexcode.wedatecompose.R.string as AppText

@Composable
fun IdentityScreen(
    modifier: Modifier = Modifier,
    openScreen: (String) -> Unit,
    viewModel: IdentifyScreenViewModel = hiltViewModel()
) {

    val user by viewModel.user

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
                .fieldModifier(),
        ) {
            BasicField(
                text = AppText.dd,
                value = user.dateOfBirth,
                onNewValue = viewModel::onDateOfBirthChange
            )
            Spacer(modifier = modifier.width(20.dp))
            BasicField(
                text = AppText.mm,
                value = user.monthOfBirth,
                onNewValue = viewModel::onMonthOfBirthChange
            )
            Spacer(modifier = modifier.width(20.dp))

            BasicField(
                text = AppText.yy,
                value = user.yearOfBirth,
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
                //viewModel.saveYearOfBirth()
            }
        }


    }

}

@Composable
fun GenderSelector(
    onSearchOptionClick: (String) -> Unit,
    isSelected: (String) -> Boolean,
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
