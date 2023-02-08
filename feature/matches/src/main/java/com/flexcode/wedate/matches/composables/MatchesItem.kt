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
import com.flexcode.wedate.matches.data.model.Matches

@Composable
fun MatchesItem(
    match:Matches,
) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(topEnd = 45.dp, topStart = 8.dp, bottomEnd = 8.dp, bottomStart = 8.dp),
        modifier = Modifier.padding(5.dp)
        ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://images.unsplash.com/photo-1566753323558-f4e0952af115?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1021&q=80")
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.sharon),
            contentDescription = "match with ${match.firstName}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(110.dp)
                .height(130.dp)
        )
    }

}