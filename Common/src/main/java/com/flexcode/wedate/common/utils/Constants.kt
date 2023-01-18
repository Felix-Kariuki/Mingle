package com.flexcode.wedate.common.utils

import androidx.datastore.preferences.core.stringSetPreferencesKey

object Constants {
    const val SPLASH_TIMEOUT = 1000L
    const val MIN_PASS_LENGTH = 6
    const val DATE_LENGTH = 2
    const val YEAR_LENGTH = 4
    const val MIN_PHONE_LENGTH = 9
    const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"
    const val AUTH_PREFERENCES = "AUTH_PREF"


    val USER_NAME_KEY = stringSetPreferencesKey("user_name_key")
    val USER_EMAIL_KEY = stringSetPreferencesKey("user_email_key")
    val USER_PHONE_KEY = stringSetPreferencesKey("user_phone_key")

    val GENDER_KEY = stringSetPreferencesKey("gender_key")
    val INTEREST_KEY = stringSetPreferencesKey("interest_key")
    val PASSWORD = stringSetPreferencesKey("password")
    val Y_O_B_KEY = stringSetPreferencesKey("y_o_b_key")
    val USER_DATA = stringSetPreferencesKey("user_data")
}