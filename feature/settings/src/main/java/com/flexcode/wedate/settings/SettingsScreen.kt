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
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flexcode.wedate.common.R.string as AppText
import com.flexcode.wedate.common.composables.*
import com.flexcode.wedate.common.extestions.basicButton
import com.flexcode.wedate.common.theme.deepBrown
import com.flexcode.wedate.common.theme.deepLightPurple
import com.flexcode.wedate.common.theme.purpleGrey
import com.flexcode.wedate.common.utils.contact_utils.sendMail
import kotlinx.coroutines.CoroutineScope
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
                height = 20.dp,
                width = 20.dp,
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
            val showDialog = remember {
                mutableStateOf(false)
            }
            if (showDialog.value) {
                DeleteAccountDialog(
                    viewModel = viewModel,
                    scope = scope,
                    context = context,
                    showDialog = showDialog.value,
                    onDismiss = { showDialog.value = false }
                )
            }

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

            InfoColumn(
                AppText.help,
                "",
                modifier = Modifier.clickable {
                    context.sendMail(
                        "weminglesingle@gmail.com",
                        "Help "
                    )
                }
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(
                AppText.ask_a_quiz,
                "",
                modifier = Modifier.clickable {
                    context.sendMail(
                        "weminglesingle@gmail.com",
                        "Question From ${state.userDetails?.firstName}"
                    )
                }
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(
                AppText.raise_a_concern,
                "",
                modifier = Modifier.clickable {
                    context.sendMail(
                        "weminglesingle@gmail.com",
                        "Concern From ${state.userDetails?.firstName}"
                    )
                }
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(
                AppText.contact_us,
                "",
                modifier = Modifier.clickable {
                    context.sendMail(
                        "weminglesingle@gmail.com",
                        "Message From ${state.userDetails?.firstName}"
                    )
                }
            )

            BasicText(
                text = AppText.more,
                color = deepBrown,
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = deepLightPurple),
                fontWeight = FontWeight.Bold
            )
            InfoColumn(
                AppText.request_a_feature,
                "",
                modifier = Modifier.clickable {
                    context.sendMail("weminglesingle@gmail.com", "Feature Request")
                }
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(
                AppText.privacy_policy,
                "",
                modifier = Modifier.clickable {
                }
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            InfoColumn(
                AppText.term_of_service,
                "",
                modifier = Modifier.clickable {
                }
            )

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

            BasicTextButton(
                text = AppText.delete_account,
                modifier = modifier.height(50.dp),
                color = MaterialTheme.colors.onBackground
            ) {
                showDialog.value = true
            }
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
                text = "Version ${getVersionName(context)}",
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

@Composable
fun DeleteAccountDialog(
    viewModel: SettingsViewModel,
    scope: CoroutineScope,
    context: Context,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "Are you sure you want to delete your account",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            text = {
                Text(
                    text = "By deleting your account you will loose all your data which " +
                        "is not recoverable by any means. To delete Click on the Confirm Button" +
                        "We will be sad if you leave 💔",
                    fontSize = 17.sp
                )
            },
            confirmButton = {
                BasicButton(text = AppText.delete_account, modifier = Modifier) {
                    Toast.makeText(
                        context,
                        "Account will be deleted after 72 hours",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.signOut()
                    scope.launch {
                        delay(1000)
                        restartApp(context)
                    }
                    /*viewModel.deleteUser()
                    viewModel.deleteUserAccount("DELETED")
                    scope.launch {
                        delay(1000)
                        restartApp(context)
                    }*/
                }
            },
            dismissButton = {
                BasicButton(text = AppText.cancel, modifier = Modifier) {
                    onDismiss()
                }
            }
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

fun getVersionName(context: Context): String {
    return try {
        context.packageManager
            .getPackageInfo(context.packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }
}
