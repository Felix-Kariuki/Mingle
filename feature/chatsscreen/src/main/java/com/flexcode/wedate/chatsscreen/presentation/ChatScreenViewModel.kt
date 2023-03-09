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
package com.flexcode.wedate.chatsscreen.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.auth.domain.usecase.UseCaseContainer
import com.flexcode.wedate.chatsscreen.domain.use_cases.ChatsUseCaseContainer
import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.ext.isNameValid
import com.flexcode.wedate.common.snackbar.SnackBarManager
import com.flexcode.wedate.common.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val useCaseContainer: UseCaseContainer,
    private val chatsUseCaseContainer: ChatsUseCaseContainer,
    private val auth: FirebaseAuth,
    logService: LogService
) : BaseViewModel(logService = logService) {

    var state = mutableStateOf(ChatScreenState())
        private set

    private val msg
        get() = state.value.message

    fun onMessageChange(value: String) {
        state.value = state.value.copy(message = value)
    }

    fun sendMessage(
        message: String,
        messageSenderId: String,
        messageTimeStamp: Long,
        lastMsgTime: Long,
        matchId: String
    ) {
        if (!msg.isNameValid()) {
            SnackBarManager.showMessage(R.string.msg_error)
            return
        }
        viewModelScope.launch {
            chatsUseCaseContainer.saveChatToMatchUseCase.invoke(
                matchId = matchId,
                lastMsg = msg,
                lastMsgTime = lastMsgTime,
                message = msg,
                messageTime = messageTimeStamp,
                messageSenderId = messageSenderId
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        Timber.i("Loading ,, Sending message....")
                    }
                    is Resource.Success -> {
                        Timber.i("SUCCESS ,, message sent")
                    }
                    is Resource.Error -> {
                        Timber.i("ERROR ,, ${result.message}")
                    }
                }
            }
        }

        viewModelScope.launch {
            chatsUseCaseContainer.saveChatToCurrentUserUseCase.invoke(
                matchId = matchId,
                lastMsg = message,
                lastMsgTime = lastMsgTime,
                message = message,
                messageTime = messageTimeStamp,
                messageSenderId = messageSenderId
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        Timber.i("Loading ,, Sending message....")
                    }
                    is Resource.Success -> {
                        Timber.i("SUCCESS ,, message sent")
                    }
                    is Resource.Error -> {
                        Timber.i("ERROR ,, ${result.message}")
                    }
                }
            }
        }
    }

    fun getUserDetails(id: String) {
        viewModelScope.launch {
            useCaseContainer.getUserDetailsUseCase(uid = id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            userDetails = result.data
                        )
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {}
                }
            }
        }
    }

    fun getAllMessages(messagesId: String) {
        viewModelScope.launch {
            chatsUseCaseContainer.getMessagesUseCase(messagesId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state.value = result.data?.let {
                            state.value.copy(
                                messages = it
                            )
                        }!!
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Timber.i("SUCCESS_ERROR:: ${result.data}")
                    }
                }
            }
        }
    }

    fun getUid(): String {
        return auth.uid!!
    }
}
