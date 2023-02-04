package com.flexcode.wedate.account

import com.flexcode.wedate.auth.data.models.User

data class AccountState(
    var isLoading:Boolean = false,
    var userDetails: User? = null
)
