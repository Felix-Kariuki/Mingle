package com.flexcode.wedate.admirers.presentation

import com.flexcode.wedate.home.data.model.Likes

data class AdmirersState(
    //val admirers: HashMap<String, Any>? = null
    val admirers: List<Likes> = emptyList()
)