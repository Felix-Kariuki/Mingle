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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.extestions.textPadding
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.common.theme.lightPink

@Composable
fun BasicText(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    fontSize: TextUnit = 16.sp,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = stringResource(text),
        fontSize = fontSize,
        color = color,
        textAlign = textAlign,
        fontWeight = fontWeight,
        modifier = modifier.textPadding()
    )
}

@Composable
fun ResultText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    fontSize: TextUnit = 16.sp,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = text,
        fontSize = fontSize,
        color = color,
        textAlign = textAlign,
        fontWeight = fontWeight,
        modifier = modifier.textPadding()
    )
}

@Composable
fun ExtraScreenText(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.SemiBold,
    fontSize: TextUnit = 20.sp
) {
    Text(
        text = stringResource(text),
        fontSize = fontSize,
        color = color,
        textAlign = textAlign,
        fontWeight = fontWeight,
        modifier = modifier.textPadding(),
        fontFamily = FontFamily.Monospace
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
fun AppTitleText(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 40.sp,
    fontWeight: FontWeight = FontWeight.ExtraBold,
    text: Int = R.string.app_name,
    color: Color = lightPink,
    textColor: Color = Color.White
) {
    val offset = Offset(5.0f, 10.0f)

    Text(
        text = stringResource(id = text),
        modifier = modifier.padding(top = 10.dp),
        fontWeight = fontWeight,
        fontSize = fontSize,
        color = textColor,
        style = TextStyle(
            shadow = Shadow(
                color = color,
                offset = offset,
                blurRadius = 3f
            )
        )
    )
}
