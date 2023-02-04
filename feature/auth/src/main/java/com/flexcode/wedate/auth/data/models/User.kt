package com.flexcode.wedate.auth.data.models

import com.google.firebase.firestore.DocumentId

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
    val datingStatus:String = "Single"
)