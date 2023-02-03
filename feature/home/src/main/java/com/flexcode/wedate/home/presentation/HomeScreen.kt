package com.flexcode.wedate.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.flexcode.wedate.common.composables.ResultText
import com.flexcode.wedate.home.data.potentialMatches

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val isEmpty = remember{
        mutableStateOf(false)
    }

        Column(
            modifier = modifier
                .fillMaxSize()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            if (!isEmpty.value) {
                PersonsCardStack(
                    items = potentialMatches,
                    onEmptyStack = {
                        isEmpty.value = true
                    }
                )
            } else {
                ResultText(text = "No more People Within your range adjust settings..", fontWeight = FontWeight.Bold)
            }
        }

}