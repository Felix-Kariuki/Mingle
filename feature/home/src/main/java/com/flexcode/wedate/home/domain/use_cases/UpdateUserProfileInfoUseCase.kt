package com.flexcode.wedate.home.domain.use_cases

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class UpdateUserProfileInfoUseCase constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(
        latitude: String,
        longitude: String,
        locationName: String
    ): Flow<Resource<Any>> {
        return repository.updateUserProfileInfo(
            longitude = longitude,
            latitude = latitude,
            locationName = locationName
        )
    }
}