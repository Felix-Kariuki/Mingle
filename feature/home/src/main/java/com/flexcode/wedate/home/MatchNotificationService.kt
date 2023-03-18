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
package com.flexcode.wedate.home

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.flexcode.wedate.common.R.drawable as AppIcon
import com.flexcode.wedate.common.utils.Constants

class MatchNotificationService(
    private val context: Context
) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)as
        NotificationManager
    fun showNotification(match: String) {
        val activityIntent = Intent(context, context.applicationContext::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val notification = NotificationCompat.Builder(context, Constants.MATCH_CHANNEL_ID)
            .setSmallIcon(AppIcon.logo)
            .setContentTitle("New Match")
            .setContentText("You matched with $match")
            .setContentIntent(activityPendingIntent)
            .setDefaults(Notification.DEFAULT_SOUND)
            .build()
        // .setStyle()

        notificationManager.notify(1, notification)
    }
}
