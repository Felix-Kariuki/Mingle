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
package com.flexcode.wedate.common.ext

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

fun Modifier.fieldModifier(): Modifier {
    return this.fillMaxWidth().padding(16.dp, 2.dp)
}

fun Modifier.textPadding(): Modifier {
    return this.fillMaxWidth().padding(16.dp, 8.dp)
}

fun Modifier.basicButton(): Modifier {
    return this.fillMaxWidth().padding(16.dp, 8.dp)
}

fun Modifier.moveTo(
    x: Float,
    y: Float
) = this.then(
    Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        layout(placeable.width, placeable.height) {
            placeable.placeRelative(x.roundToInt(), y.roundToInt())
        }
    }
)

fun Modifier.visible(
    visible: Boolean = true
) = this.then(
    Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        if (visible) {
            layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        } else {
            layout(0, 0) {}
        }
    }
)
