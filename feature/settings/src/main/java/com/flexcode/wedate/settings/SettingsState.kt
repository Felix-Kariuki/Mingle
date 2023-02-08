package com.flexcode.wedate.settings

import com.flexcode.wedate.auth.data.models.User

data class SettingsState(
    var userDetails: User? = null
)
