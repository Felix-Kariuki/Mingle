package com.flexcode.wedatecompose

import com.flexcode.wedatecompose.di.FirebaseModule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [FirebaseModule::class])
object TestFirebaseModule {
    private const val HOST = "10.0.2.2"
    private const val AUTH_PORT = 9099
    private const val FIRESTORE_PORT = 8080
    private const val FIREBASE_STORAGE = 7782

    @Provides
    fun auth(): FirebaseAuth = Firebase.auth.also { it.useEmulator(HOST, AUTH_PORT) }

    @Provides
    fun firestore(): FirebaseFirestore = Firebase.firestore.also { it.useEmulator(HOST, FIRESTORE_PORT) }

    @Provides
    fun storage(): FirebaseStorage = Firebase.storage.also {
        it.useEmulator(HOST, FIREBASE_STORAGE)
    }
}