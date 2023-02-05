package com.flexcode.wedate.auth.domain.usecase

data class UseCaseContainer(
    val loginUseCase: LoginUseCase,
    val registerUseCase: RegisterUseCase,
    val getUserDetailsUseCase: GetUserDetailsUseCase,
    val updateUserProfileInfoUseCase: UpdateUserProfileInfoUseCase
)
