package com.flexcode.wedate.matches.presentation

import com.flexcode.wedate.matches.data.model.Matches

data class MatchesState(
    val isLoading: Boolean = true,
    val searchValue:String = "",
    val matches: List<Matches> = emptyList()
)
