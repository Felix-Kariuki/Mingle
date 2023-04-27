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

data class UserSubscriptions(
    val seeDistanceBetweenYouAndCrush:Int = -1,
    val seeDistanceBetweenYouAndCrushTime: Long = 0L,
    val seeAdmirersOnMap : Int = -1,
    val seeAdmirersTime: Long = 0L,
    val hideYourLocationFromOthers: Int = -1,
    val hideYourLocationFromOthersTime: Long = 0L,
    val lifeTimeSubscriptions: Int = -1,   //15000
    val lifeTimeSubscriptionsTime:Long= 0L
    /**
     *  -1 is for false 1000 for success
     */
)

/**
 * save last login time at home and if last login time is not greater than
 * <expire time hours(9)>  don't update else update and if its greater or ==
 * update and change the swipeTimes to 0
 */

/**
 * save the swipes expired time exactly at the last swipe made then:
 * perfom checks on home screen to see if cureent timestamp minus the `swipesExpiredTime`
 * is <expire time hours(9)> or more if so reset swiped times to 0 and at expiration update
 * `swipesExpiredTime`
 */