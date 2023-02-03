package com.flexcode.wedate.matches.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.flexcode.wedate.common.R

@Composable
fun MatchesItem(
    image: String,
    name: String
) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(topEnd = 45.dp, topStart = 8.dp, bottomEnd = 8.dp, bottomStart = 8.dp),
        modifier = Modifier.padding(5.dp)
        ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.sharon),
            contentDescription = "match with $name",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(110.dp)
                .height(130.dp)
        )
    }

}