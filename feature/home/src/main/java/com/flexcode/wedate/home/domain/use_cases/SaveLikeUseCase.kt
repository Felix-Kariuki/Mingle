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
package com.flexcode.wedate.home.domain.use_cases

import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class SaveLikeUseCase constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(
        crushUserId: String,
        firstName: String,
        locationName: String,
        years: String,
        lat: String,
        long: String,
        profileImage: String,
        matched: Boolean
    ): Flow<Resource<Any>> {
        return repository.saveLike(
            crushUserId,
            firstName,
            locationName,
            years,
            lat,
            long,
            profileImage,
            matched
        )
    }
}
