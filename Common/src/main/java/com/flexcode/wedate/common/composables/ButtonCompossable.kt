package com.flexcode.wedate.common.composables

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.flexcode.wedate.common.theme.lightPurple

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