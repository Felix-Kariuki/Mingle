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
package com.flexcode.wedate.auth.data.models

data class User(
    val id: String = "",
    val isAnonymous: Boolean = true,
    val firstName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = "",
    val dateOfBirth: String = "",
    val monthOfBirth: String = "",
    val yearOfBirth: String = "",
    val years: String = "",
    val gender: String = "",
    val interestedIn: String = "",
    val searchingFor: String = "",
    val profileImage: ProfileImage? = null,
    val isFree: Boolean = true,
    val datingStatus: String = "Single",
    val locationName: String = "",
    val latitude: String = "0.0",
    val longitude: String = "0.0",
    val isOnline: Boolean = false,
    val likedBy: HashMap<String, Any>? = null
)
