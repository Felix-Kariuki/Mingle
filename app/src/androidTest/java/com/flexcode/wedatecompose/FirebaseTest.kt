/*
 * Copyright 2023 Felix Kariuki.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flexcode.wedatecompose

import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
class FirebaseTest {

    @get:Rule
    val hilt = HiltAndroidRule(this)

    // @Inject lateinit var registrationRepository: StoreRegistrationRepository
    @Inject lateinit var firestore: FirebaseFirestore

    @Before
    fun setUp() {
        hilt.inject()
        runBlocking { firestore.clearPersistence().await() }
    }

    /*@Test
    fun test() = runTest {
        val newId = registrationRepository.saveUserDetails(User(id ="FFggtks"))
        val result = registrationRepository.user.first()
        assertThat(result).containsExactly(User(id = newId))
    }*/
}
