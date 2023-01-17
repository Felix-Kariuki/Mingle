package com.flexcode.wedatecompose.common.composables

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedatecompose.presentation.profile_images_screen.ProfileImagesViewModel
import com.flexcode.wedatecompose.ui.theme.lightPurple
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun BasicButton(
    @StringRes text: Int,
    modifier: Modifier,
    action: () -> Unit,
) {
    Button(
        onClick = action,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = lightPurple,
            contentColor = MaterialTheme.colors.onPrimary
        ),
    ) {
        Text(text = stringResource(text), fontSize = 16.sp)
    }
}

@Composable
fun BasicTextButton(
    @StringRes text: Int,
    modifier: Modifier,
    color : Color,
    action: () -> Unit
) {
    TextButton(
        onClick = action,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = color
        ),
        //interactionSource =
    ) {
        Text(text = stringResource(text))
    }
}


/*class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}*/