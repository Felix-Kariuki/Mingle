package com.flexcode.wedate.auth.domain.usecase

import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.common.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetAllUsersUseCase constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke() : Flow<Resource<List<User>>>{
        return repository.getAllUsers()
    }
}