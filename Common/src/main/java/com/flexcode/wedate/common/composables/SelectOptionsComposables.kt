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

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.flexcode.wedate.common.theme.lightPurple

@Composable
fun SelectOption(
    option: String,
    onClick: (String) -> Unit,
    isSelected: (String) -> Boolean
) {
    Card(
        Modifier
            .wrapContentHeight()
            .fillMaxSize(1f)
            .padding(start = 16.dp, end = 16.dp, bottom = 50.dp, top = 15.dp)
            .clickable {
                onClick(option)
            }
            .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected(option)) {
                lightPurple
            } else {
                MaterialTheme.colors.background
            }
        )
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = option,
                textAlign = TextAlign.Center,
                style = androidx.compose.material3.MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (isSelected(option)) {
                    androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
                } else {
                    androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}
