package com.flexcode.wedate.admirers.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.flexcode.wedate.common.composables.ResultText
import com.flexcode.wedate.common.composables.SwipeRightLeftIcon
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.home.data.model.Likes

@Composable
fun AdmirerItem(
    like: Likes,
    modifier: Modifier
) {
    Column {
        Box(
            modifier = modifier
                .padding(10.dp)
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 8.dp, topEnd = 30.dp,
                        bottomStart = 8.dp, bottomEnd = 8.dp
                    )
                )
                .height(200.dp)
        ) {
            Card {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1566753323558-f4e0952af115?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1021&q=80",
                    contentDescription = "Admired by ${like.firstName}",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                )
            }
            Column(
                modifier = modifier
                    .align(Alignment.BottomStart)
            ) {
                ResultText(
                    text = "${like.firstName},${like.years}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SwipeRightLeftIcon(
                onClick = { /*TODO*/ },
                icon = Icons.Default.Close,
                contentDesc = "Dislike ${like.firstName}",
                tint = Color.Black,
                paddingValues = PaddingValues(0.dp),
                height = 30.dp,
                width = 30.dp
            )

            SwipeRightLeftIcon(
                onClick = { /*TODO*/ },
                icon = Icons.Default.FavoriteBorder,
                contentDesc = "Like ${like.firstName}",
                tint = deepBrown,
                paddingValues = PaddingValues(0.dp),
                height = 30.dp,
                width = 30.dp
            )
        }
    }
}