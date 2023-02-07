package com.flexcode.wedate.home.domain.use_cases

import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetAllUsersUseCase constructor(
    private val repository: HomeRepository
) {

    suspend operator fun invoke() : Flow<Resource<List<User>>>{
        return repository.getAllUsers()
    }
}