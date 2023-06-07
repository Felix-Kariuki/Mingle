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
package com.flexcode.wedatecompose

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.flexcode.wedate.common.utils.Constants
import com.flexcode.wedatecompose.crashlyics.CrashlyticsTree
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WeDateHiltApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        createNotificationChannel()
       // Firebase.database.setPersistenceEnabled(true)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.MATCH_CHANNEL_ID,
                "Matches",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Used to show when you match with someone notification"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun initTimber() = when {
        BuildConfig.DEBUG -> {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return super.createStackElementTag(element) + ":" + element.lineNumber
                }
            })
        } else -> {
            Timber.plant(CrashlyticsTree())
        }
    }
}
