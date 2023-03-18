package com.flexcode.wedate.home.domain.use_cases

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class DeleteLikedByFromUseCase constructor(
   private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(userLikedById:String) : Flow<Resource<Any>>{
        return homeRepository.deleteLikedByFromMe(userLikedById)
    }
}