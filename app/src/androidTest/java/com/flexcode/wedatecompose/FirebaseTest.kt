package com.flexcode.wedatecompose

import com.flexcode.wedate.auth.data.models.User
import com.google.common.truth.Truth.assertThat
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class FirebaseTest {

    @get:Rule
    val hilt = HiltAndroidRule(this)

    @Inject lateinit var registrationRepository: StoreRegistrationRepository
    @Inject lateinit var firestore: FirebaseFirestore

    @Before
    fun setUp() {
        hilt.inject()
        runBlocking { firestore.clearPersistence().await() }
    }

    @Test
    fun test() = runTest {
        val newId = registrationRepository.saveUserDetails(User(id ="FFggtks"))
        val result = registrationRepository.user.first()
        assertThat(result).containsExactly(User(id = newId))
    }
}