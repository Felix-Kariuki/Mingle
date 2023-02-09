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
package com.flexcode.wedate.matches.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.flexcode.wedate.common.R.drawable as Appicon
import com.flexcode.wedate.common.composables.ResultText

@Composable
fun ChatItem(
    name: String,
    image: String,
    message: String
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(Appicon.sharon),
            contentDescription = "chats$name",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(CircleShape)
                .size(80.dp)
        )
        Column {
            ResultText(text = name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

            ResultText(text = message, color = Color.Gray)
        }
    }
}
