package com.flexcode.wedate.chatsscreen.data.model

data class ChatProfile(
    val id: String = "",
    val firstName: String = "",
    val profileImage: String= "",
    val lastMsgTime: Long? = null,
    val lastMsg: String = ""
)
