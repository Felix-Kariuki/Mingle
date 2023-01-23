package com.flexcode.wedate.auth.data.models

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val id: String = "",
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
    val profileImage1:String = "",
    val profileImage2:String = "",
    val profileImage3:String = "",
    val profileImage4:String = "",
    val profileImage5:String = "",
    val profileImage6:String = "",
    val isFree: Boolean = true,
    val datingStatus:String = "Single"
)