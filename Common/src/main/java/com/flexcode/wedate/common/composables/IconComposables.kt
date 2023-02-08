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
    contentDesc:String,
    tint: Color = Color.White
) {
    IconButton(
        modifier = modifier.padding(paddingValues),
        onClick = { onClick()  }
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