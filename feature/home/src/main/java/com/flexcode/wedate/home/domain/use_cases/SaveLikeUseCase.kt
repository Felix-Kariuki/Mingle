package com.flexcode.wedate.home.domain.use_cases

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class SaveLikeUseCase constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(
        crushUserId: String, firstName: String, locationName: String, years: String,
        lat: String, long: String, profileImage: String, matched:Boolean
    ): Flow<Resource<Any>> {
        return repository.saveLike(crushUserId, firstName, locationName,years,lat, long, profileImage,matched)
    }
}