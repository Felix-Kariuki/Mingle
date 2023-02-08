package com.flexcode.wedate.matches.domain.repository

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.matches.data.model.Matches
import kotlinx.coroutines.flow.Flow

interface MatchesRepository {
    suspend fun getAllUserMatches() : Flow<Resource<List<Matches>>>

}