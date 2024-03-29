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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.flexcode.wedate.common.ImageShimmer
import com.flexcode.wedatecompose.network.data.models.matches.Matches

@Composable
fun MatchesItem(
    match: Matches,
    navigateToChats: (String) -> Unit
) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(
            topEnd = 45.dp,
            topStart = 8.dp,
            bottomEnd = 8.dp,
            bottomStart = 8.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(match.profileImage)
                .crossfade(true)
                .placeholder(ImageShimmer().shimmerDrawable)
                .build(),
            contentDescription = "chat with ${match.firstName}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(110.dp)
                .height(130.dp)
                .clickable { navigateToChats(match.id) }
        )
    }
}
