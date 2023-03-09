package com.flexcode.wedate.matches.domain.use_case

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.matches.data.model.ChatProfile
import com.flexcode.wedate.matches.domain.repository.MatchesRepository
import kotlinx.coroutines.flow.Flow

class GetChatProfilesUseCase(private val repository: MatchesRepository){
    suspend operator fun invoke(): Flow<Resource<List<ChatProfile>>> {
        return repository.getAllChatProfiles()
    }
}