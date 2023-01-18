package com.flexcode.wedate.common.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.flexcode.wedate.common.R

@Composable
fun LogoComposable() {

    Image(
        painter = painterResource( R.drawable.logo),
        contentDescription = stringResource(R.string.logo),
        modifier = Modifier.size(50.dp)
    )
    AppTitleText()

}