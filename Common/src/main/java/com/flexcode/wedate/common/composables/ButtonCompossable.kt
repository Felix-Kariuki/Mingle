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
    backGroundColor:Color  = lightPurple,
    contentColor:Color  = MaterialTheme.colors.onPrimary,
    action: () -> Unit,
) {
    Button(
        onClick = action,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backGroundColor,
            contentColor = contentColor
        )
    ) {
        Text(text = stringResource(text), fontSize = 16.sp)
    }
}

@Composable
fun BasicTextButton(
    @StringRes text: Int,
    modifier: Modifier,
    color: Color,
    action: () -> Unit
) {
    TextButton(
        onClick = action,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = color
        )
        // interactionSource =
    ) {
        Text(text = stringResource(text))
    }
}

/*class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}*/
