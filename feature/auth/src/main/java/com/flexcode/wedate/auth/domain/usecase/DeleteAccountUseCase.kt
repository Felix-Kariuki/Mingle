package com.flexcode.wedate.auth.domain.usecase

import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.common.utils.Resource
import kotlinx.coroutines.flow.Flow

class DeleteAccountUseCase constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(accountStatus:String) : Flow<Resource<Any>>{
        return repository.deleteAccount(accountStatus)
    }
}