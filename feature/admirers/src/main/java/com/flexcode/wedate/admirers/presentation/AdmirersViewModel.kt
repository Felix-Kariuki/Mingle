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
package com.flexcode.wedate.admirers.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.domain.usecase.UseCaseContainer
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.utils.Resource
import com.flexcode.wedate.home.domain.use_cases.HomeUseCases
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class AdmirersViewModel @Inject constructor(
    logService: LogService,
    private val homeUseCases: HomeUseCases,
    private val useCases: UseCaseContainer,
    private val auth: FirebaseAuth
) : BaseViewModel(logService) {

    var state = mutableStateOf(AdmirersState())
        private set

    init {
        getAllLikedBy(getUid())
        getUserDetails()
    }

    private fun getAllLikedBy(currentUserId: String) {
        viewModelScope.launch {
            homeUseCases.getAllLikedByUseCase.invoke(currentUserId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = result.data?.filter { !it.matched }
                            ?.let { state.value.copy(admirers = it) }!!
                        Timber.i("LIKED: ${result.data} ")
                        // state.value = result.data?.let { state.value.copy(admirers = it) }!!
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("LIKES ERROR::: ${result.message}")
                    }
                }
            }
        }
    }

    fun getUid(): String {
        return auth.uid!!
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            useCases.getUserDetailsUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = state.value.copy(userDetails = result.data)
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("INTERESTS IN ERROR::: ${result.message}")
                    }
                }
            }
        }
    }

    fun saveLikeToCrush(
        crushUserId: String,
        firstName: String,
        locationName: String,
        years: String,
        lat: String,
        long: String,
        profileImage: String,
        matched: Boolean
    ) {
        viewModelScope.launch {
            homeUseCases.saveLikeUseCase.invoke(
                crushUserId,
                firstName,
                locationName,
                years,
                lat,
                long,
                profileImage,
                matched
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {}
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("SAVE CRUSH ERROR::: ${result.message}")
                    }
                }
            }
        }
    }

    fun saveMatchToCrush(
        crushUserId: String,
        firstName: String,
        locationName: String,
        years: String,
        lat: String,
        long: String,
        profileImage: String
    ) {
        viewModelScope.launch {
            homeUseCases.saveMatchUseCase.invoke(
                crushUserId,
                firstName,
                locationName,
                years,
                lat,
                long,
                profileImage
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {}
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("SAVE MATCH ERROR::: ${result.message}")
                    }
                }
            }
        }
    }

    fun saveMatchToCurrentUser(
        crushUserId: String,
        firstName: String,
        locationName: String,
        years: String,
        lat: String,
        long: String,
        profileImage: String
    ) {
        viewModelScope.launch {
            homeUseCases.saveMatchToCurrentUserUseCase.invoke(
                crushUserId,
                firstName,
                locationName,
                years,
                lat,
                long,
                profileImage
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {}
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("SAVE MATCH ERROR::: ${result.message}")
                    }
                }
            }
        }
    }

    /*private fun getAdmirers() {
        viewModelScope.launch {
            useCaseContainer.getUserDetailsUseCase.invoke().collect {result->
                when (result) {
                    is Resource.Success -> {
                        state.value = state.value.copy(admirers = result.data?.likedBy)
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.e("INTERESTS IN ERROR::: ${result.message}")
                    }
                }
            }
        }
    }*/
}
