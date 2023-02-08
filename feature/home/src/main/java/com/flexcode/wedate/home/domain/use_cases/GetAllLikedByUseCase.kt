package com.flexcode.wedate.home.domain.use_cases

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.home.data.model.Likes
import com.flexcode.wedate.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetAllLikedByUseCase constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(currentUserId:String): Flow<Resource<List<Likes>>>{
        return repository.getAllLikedBy(currentUserId)
    }

}