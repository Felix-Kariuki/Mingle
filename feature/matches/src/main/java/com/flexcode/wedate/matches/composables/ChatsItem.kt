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
import com.flexcode.wedate.common.composables.ResultText
import com.flexcode.wedate.common.R.drawable as Appicon

@Composable
fun ChatItem(
    name:String,
    image:String,
    message:String
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
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