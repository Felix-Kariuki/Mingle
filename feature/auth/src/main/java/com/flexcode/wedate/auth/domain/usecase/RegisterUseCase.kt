package com.flexcode.wedate.auth.domain.usecase

import com.flexcode.wedate.auth.domain.repository.AuthRepository
import com.flexcode.wedate.common.utils.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

class RegisterUseCase constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        firstName: String,
        phoneNumber: String,
        email: String,
        password: String,
        dateOfBirth: String,
        monthOfBirth: String,
        yearOfBirth: String,
        years: String,
        gender: String,
        interestedIn: String,
        searchingFor: String,
    ): Flow<Resource<AuthResult>> {
        return repository.register(
            firstName = firstName,
            phoneNumber = phoneNumber,
            email = email,
            password = password,
            dateOfBirth = dateOfBirth,
            monthOfBirth = monthOfBirth,
            yearOfBirth = yearOfBirth,
            years = years,
            gender = gender,
            interestedIn = interestedIn,
            searchingFor = searchingFor

        )
    }
}