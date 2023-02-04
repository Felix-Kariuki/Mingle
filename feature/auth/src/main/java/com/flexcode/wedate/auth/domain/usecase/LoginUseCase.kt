package com.flexcode.wedate.auth.domain.usecase

import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.common.utils.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

class LoginUseCase constructor(
    private val repository: AuthRepository
) {
    suspend  operator fun invoke(email: String,password: String): Flow<Resource<AuthResult>> {
        return repository.login(email, password)
    }
}