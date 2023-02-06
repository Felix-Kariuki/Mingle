package com.flexcode.wedate.home.presentation

import com.flexcode.wedate.auth.data.models.User

data class HomeUiState(
    val potentialMatches: MutableList<User> = arrayListOf(),
    val isLoading: Boolean = false,
    var isEmpty: Boolean = true

)