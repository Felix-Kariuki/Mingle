package com.flexcode.wedate.matches.domain.use_case

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.matches.data.model.Matches
import com.flexcode.wedate.matches.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow

class GetAllUserMatchesUseCase(
    private val repository: MatchesRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Matches>>> {
        return repository.getAllUserMatches()
    }
}