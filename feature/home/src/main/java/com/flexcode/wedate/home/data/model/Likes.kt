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
package com.flexcode.wedate.home.data.model

data class Likes(
    val id: String = "",
    val date: Long? = null,
    val firstName: String = "",
    val locationName: String = "",
    val years: String = "",
    val lat: String = "",
    val long: String = "",
    val profileImage: String = "",
    val matched: Boolean = false
)
