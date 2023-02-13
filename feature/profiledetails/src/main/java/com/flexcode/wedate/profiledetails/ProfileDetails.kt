package com.flexcode.wedate.profiledetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.flexcode.wedate.common.theme.lightPurple

@Composable
fun ProfileDetailsScreen(
    navigateToEditProfile: () -> Unit,
    navigateToAccountScreen: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier.padding(bottom = 65.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToEditProfile() },
                backgroundColor = lightPurple,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Profile")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {

        }
    }

}