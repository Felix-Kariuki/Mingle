package com.flexcode.wedate.auth.domain.usecase

import androidx.compose.runtime.MutableState
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.common.utils.Resource
import kotlinx.coroutines.flow.Flow

class UpdateUserProfileInfoUseCase constructor(
    private val repository: AuthRepository
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