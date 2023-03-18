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
package com.flexcode.wedate.settings

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.common.R.string as AppText
import com.flexcode.wedate.common.composables.*
import com.flexcode.wedate.common.ext.basicButton
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.common.theme.deepLightPurple
import com.flexcode.wedate.common.theme.purpleGrey
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    openAndPopUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val state by viewModel.state

    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            SwipeRightLeftIcon(
                onClick = { openAndPopUp() },
                icon = Icons.Default.ArrowBackIos,
                contentDesc = "Back",
                height = 30.dp,
                width = 30.dp,
                paddingValues = PaddingValues(start = 10.dp, 0.dp),
                tint = Color.Black
            )
            Row(horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth()) {
                ResultText(text = "Settings", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            BasicText(
                text = AppText.account,
                color = deepBrown,
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = deepLightPurple),
                fontWeight = FontWeight.Bold
            )
            InfoColumn(AppText.name, "${state.userDetails?.firstName}")
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(AppText.birthday, "${state.userDetails?.dateOfBirth}")
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(AppText.phone_number, "${state.userDetails?.phoneNumber}")
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(AppText.email, "${state.userDetails?.email}")
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(AppText.location, "${state.userDetails?.locationName}")

            BasicText(
                text = AppText.support,
                color = deepBrown,
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = deepLightPurple),
                fontWeight = FontWeight.Bold
            )

            InfoColumn(AppText.help, "")
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(AppText.ask_a_quiz, "")
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(AppText.raise_a_concern, "")
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(AppText.contact_us, "")

            BasicText(
                text = AppText.more,
                color = deepBrown,
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = deepLightPurple),
                fontWeight = FontWeight.Bold
            )
            InfoColumn(AppText.request_a_feature, "")
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(AppText.privacy_policy, "")
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(AppText.term_of_service, "")

            Button(
                onClick = {
                    viewModel.signOut()
                    scope.launch {
                        delay(1000)
                        restartApp(context)
                    }
                },
                modifier = modifier
                    .basicButton()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = purpleGrey,
                    contentColor = MaterialTheme.colors.onPrimary
                )
            ) {
                Text(text = stringResource(AppText.log_out), fontSize = 16.sp)
            }

            //enable this button at 1000 users
            /*BasicTextButton(
                text = AppText.delete_account,
                modifier = modifier.height(50.dp),
                color = MaterialTheme.colors.onBackground
            ) {
                viewModel.deleteUserAccount("DELETED")
                scope.launch {
                    delay(1000)
                    restartApp(context)
                }
            }*/
            Spacer(modifier = Modifier.height(10.dp))
            LogoComposableImage()
            AppTitleText(
                modifier = modifier,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = purpleGrey,
                textColor = purpleGrey
            )
            ResultText(
                text = "Version 1.0.0",
                color = purpleGrey,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .offset(y = (-8).dp)
                    .padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun InfoColumn(
    @StringRes text1: Int,
    text2: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 10.dp, top = 10.dp)
    ) {
        Text(
            text = stringResource(id = text1),
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            modifier = modifier.padding(16.dp, 8.dp)
        )
        Text(
            text = text2,
            fontSize = 16.sp,
            textAlign = TextAlign.End,
            modifier = modifier.padding(16.dp, 8.dp),
            fontWeight = FontWeight.SemiBold
        )
    }
}

fun restartApp(context: Context) {
    val packageManager: PackageManager = context.packageManager
    val intent: Intent = packageManager.getLaunchIntentForPackage(context.packageName)!!
    val componentName: ComponentName = intent.component!!
    val restartIntent: Intent = Intent.makeRestartActivityTask(componentName)
    context.startActivity(restartIntent)
    Runtime.getRuntime().exit(0)
}
