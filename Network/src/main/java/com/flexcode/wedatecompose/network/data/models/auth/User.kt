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
package com.flexcode.wedatecompose.network.data.models.auth

data class User(
    val id: String = "",
    val anonymous: Boolean = true,
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
    val free: Boolean = true,
    val datingStatus: String = "Single",
    val locationName: String = "",
    val latitude: String = "1.687",
    val longitude: String = "36.12547",
    val online: Boolean = true,
    val likedBy: HashMap<String, Any>? = null,
    val userBio: String = "",
    val accountStatus: String = "ACTIVE",
    val subscriptions: UserSubscriptions? = null,
    val swipes: LimitSwipes? = null,
    val nickName: String = ""
)

/**
 * save last login time at home and if last login time is not greater than
 * <expire time hours(9)>  don't update else update and if its greater or ==
 * update and change the swipeTimes to 0
 */

/**
 * save the swipes expired time exactly at the last swipe made then:
 * perfom checks on home screen to see if current timestamp minus the `swipesExpiredTime`
 * is <expire time hours(9)> or more if so reset swiped times to 0 and at expiration update
 * `swipesExpiredTime`
 */
