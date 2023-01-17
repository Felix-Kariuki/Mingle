package com.flexcode.wedatecompose.di

import com.flexcode.wedatecompose.data.LogService
import com.flexcode.wedatecompose.data.LogServiceImpl
import com.flexcode.wedatecompose.data.models.repositoryimpl.AuthRepositoryImpl
import com.flexcode.wedatecompose.data.models.repositoryimpl.ProfileImageRepositoryImpl
import com.flexcode.wedatecompose.data.models.repositoryimpl.StoreRegistrationRepositoryImpl
import com.flexcode.wedatecompose.data.repository.AuthRepository
import com.flexcode.wedatecompose.data.repository.StoreRegistrationRepository
import com.flexcode.wedatecompose.domain.repository.ProfileImageRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideStorageRepository(impl: StoreRegistrationRepositoryImpl): StoreRegistrationRepository


    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

}