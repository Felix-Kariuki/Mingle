package com.flexcode.wedate.auth.data.models

import androidx.compose.runtime.MutableState

data class User(
    val id: String = "",
    val isAnonymous: Boolean = true,
    val firstName:String ="",
    val phoneNumber:String ="",
    val email:String ="",
    val password:String ="",
    val dateOfBirth:String = "",
    val monthOfBirth:String = "",
    val yearOfBirth:String = "",
    val years:String = "",
    val gender:String = "",
    val interestedIn:String = "",
    val searchingFor:String = "",
    val profileImage: ProfileImage? = null,
    val isFree: Boolean = true,
    val datingStatus:String = "Single",
    val locationName:String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)