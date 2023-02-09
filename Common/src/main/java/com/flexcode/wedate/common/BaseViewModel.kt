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
package com.flexcode.wedate.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flexcode.wedate.common.data.LogService
import com.flexcode.wedate.common.snackbar.SnackBarManager
import com.flexcode.wedate.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel(private val logService: LogService) : ViewModel() {
    fun launchCatching(snackBar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackBar) {
                    SnackBarManager.showMessage(throwable.toSnackbarMessage())
                    Log.d(
                        "ERRORR_HAPENNED :: ",
                        "${throwable.message} ${throwable.localizedMessage}"
                    )
                }
                logService.logNonFatalCrash(throwable)
                Log.d("ERRORR_HAPENNED :: ", "${throwable.message} ${throwable.localizedMessage}")
            },
            block = block
        )
}
