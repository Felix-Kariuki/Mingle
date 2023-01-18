package com.flexcode.wedate.common.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.ext.textPadding
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.common.theme.lightPink

@Composable
fun BasicText(
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(text),
        fontSize = 16.sp,
        color = Color.Black,
        fontWeight = FontWeight.Normal,
        modifier = modifier.textPadding()
    )
}

@Composable
fun ExtraScreenText(
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(text),
        fontSize = 20.sp,
        color = Color.Black,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier.textPadding(),
        fontFamily = FontFamily.Monospace,
    )
}

@Composable
fun ScreenTitlesText(
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(text),
        fontSize = 30.sp,
        color = deepBrown,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(top = 30.dp)
    )

}

@Composable
fun AppTitleText() {
    val offset = Offset(5.0f, 10.0f)

    Text(
        text = stringResource(id = R.string.app_name),
        modifier = Modifier.padding(top = 10.dp),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 40.sp,
        color = Color.White,
        style = TextStyle(
            shadow = Shadow(
                color = lightPink,
                offset = offset,
                blurRadius = 3f
            )
        )
    )
}