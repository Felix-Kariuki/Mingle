package com.flexcode.wedate.home.data.model

data class Likes(
    val id: String = "",
    val date:Long? = null,
    val firstName:String = "",
    val locationName: String= "",
    val years:String = "",
    val lat:String = "",
    val long: String = "",
    val profileImage:String = "",
    val matched:Boolean = false
)
