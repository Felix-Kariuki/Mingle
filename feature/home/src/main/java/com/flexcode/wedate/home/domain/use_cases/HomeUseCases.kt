package com.flexcode.wedate.home.domain.use_cases

data class HomeUseCases(
    val updateUserProfileInfoUseCase: UpdateUserProfileInfoUseCase,
    val getAllUsersUseCase: GetAllUsersUseCase,
    val saveLikeUseCase: SaveLikeUseCase,
)