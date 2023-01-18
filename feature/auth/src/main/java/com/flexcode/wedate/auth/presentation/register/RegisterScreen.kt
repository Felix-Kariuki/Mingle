package com.flexcode.wedate.auth.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.common.composables.*
import com.flexcode.wedate.common.ext.basicButton
import com.flexcode.wedate.common.ext.fieldModifier
import com.flexcode.wedate.common.theme.lightPurple
import com.flexcode.wedate.common.R.string as AppText

@Composable
fun RegisterScreen(
    openScreen: (String) -> Unit,
    openAndPopUp: (String,String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
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
        contentAlignment = Alignment.Center,
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {

            Spacer(modifier = modifier.padding(top = 20.dp))

            LogoComposable()

            Spacer(modifier = modifier.padding(top = 20.dp))

            Column(
                modifier = modifier
                    .background(Color.White, shape = RoundedCornerShape(30.dp))
                    .width(370.dp)
                    .height(550.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                BasicText(text = AppText.first_name)
                EmailField(
                    value = uiState.firstName,
                    icon = Icons.Default.Person,
                    onNewValue = viewModel::onFirstNameChange,
                    Modifier.fieldModifier()
                )

                BasicText(text = AppText.phone_number)
                EmailField(
                    value = uiState.phoneNumber,
                    icon = Icons.Default.Person,
                    onNewValue = viewModel::onPhoneNumberChange,
                    Modifier.fieldModifier()
                )

                BasicText(text = AppText.email)
                EmailField(
                    value = uiState.email,
                    icon = Icons.Default.Email,
                    onNewValue = viewModel::onEmailChange,
                    Modifier.fieldModifier()
                )

                BasicText(text = AppText.password)
                PasswordField(
                    value = uiState.password, onNewValue = viewModel::onPasswordChange,
                    Modifier.fieldModifier()
                )
                BasicText(text = AppText.confirmPassword)
                ConfirmPasswordField(
                    value = uiState.confirmPassword ,
                    onNewValue = viewModel::onConfirmPasswordChange,
                    modifier.fieldModifier()
                )

                BasicButton(
                    text = AppText.register,
                    modifier = modifier
                        .basicButton()
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    viewModel.onRegisterClick(openScreen)
                }

            }

            BasicTextButton(
                text = AppText.have_account,
                modifier = modifier.basicButton(),
                color = MaterialTheme.colors.onBackground,
            ) {
                viewModel.onLoginClick(openAndPopUp)
            }
        }
    }
}