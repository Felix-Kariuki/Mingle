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
package com.flexcode.wedate.common.utils

import androidx.datastore.preferences.core.stringSetPreferencesKey

object Constants {
    const val SPLASH_TIMEOUT = 1000L

    // const val SPLASH_TIMEOUT = 1800L
    const val MIN_PASS_LENGTH = 6
    const val MIN_PHONE_LENGTH = 10
    const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"

    const val MATCH_CHANNEL_ID = "match_channel"

    const val MAX_DATE = 31
    const val MAX_MONTH = 12
    const val MIN_YEAR_LENGTH = 4

    const val AUTH_PREFERENCES = "AUTH_PREF"

    val USER_NAME_KEY = stringSetPreferencesKey("user_name_key")
    val USER_EMAIL_KEY = stringSetPreferencesKey("user_email_key")
    val USER_PHONE_KEY = stringSetPreferencesKey("user_phone_key")

    val GENDER_KEY = stringSetPreferencesKey("gender_key")
    val INTEREST_KEY = stringSetPreferencesKey("interest_key")
    val PASSWORD = stringSetPreferencesKey("password")
    val Y_O_B_KEY = stringSetPreferencesKey("y_o_b_key")
    val AGE_KEY = stringSetPreferencesKey("AGE_key")
    val USER_DATA = stringSetPreferencesKey("user_data")
    val USER_LAT_KEY = stringSetPreferencesKey("USER_LAT_key")
    val USER_LONG_KEY = stringSetPreferencesKey("USER_LONG_key")


    const val ONESIGNAL_APP_ID = "e363b214-60c5-4e1a-8b2b-********"

    const val SEE_ON_MAP = "see_admirers_on_map_sub"
    const val LOVE_CALCULATOR = "love_calculator"
}
