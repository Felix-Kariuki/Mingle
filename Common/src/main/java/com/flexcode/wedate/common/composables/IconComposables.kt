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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SwipeRightLeftIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(0.dp, 0.dp, 50.dp, 0.dp),
    icon: ImageVector,
    height: Dp = 50.dp,
    width: Dp = 50.dp,
    contentDesc: String,
    tint: Color = Color.White
) {
    IconButton(
        modifier = modifier.padding(paddingValues),
        onClick = { onClick() }
    ) {
        Icon(
            icon,
            contentDescription = contentDesc,
            tint = tint,
            modifier =
            modifier
                .height(height)
                .width(width)
        )
    }
}
