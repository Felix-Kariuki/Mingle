package com.flexcode.wedatecompose.di

import com.flexcode.wedate.auth.data.repository.ProfileImageRepositoryImpl
import com.flexcode.wedate.auth.domain.repository.ProfileImageRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ProfileImageModule {

    @Provides
    fun provideProfileImageRepository(
        storage: FirebaseStorage,
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): ProfileImageRepository = ProfileImageRepositoryImpl(
        storage = storage,
        firestore = firestore,
        auth = auth
    )
}