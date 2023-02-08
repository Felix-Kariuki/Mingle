package com.flexcode.wedate.home.presentation

import com.flexcode.wedate.auth.data.models.User

data class HomeUiState(
    val potentialMatches: MutableList<User> = arrayListOf(),
    val isLoading: Boolean = false,
    var isEmpty: Boolean = true,
    var interestedIn:String = "Everyone",
    var userDetails: User? = null
    //val likedBy:MutableList<Likes> = arrayListOf()

)