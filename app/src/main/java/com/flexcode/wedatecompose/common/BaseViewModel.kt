package com.flexcode.wedatecompose.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flexcode.wedatecompose.common.snackbar.SnackBarManager
import com.flexcode.wedatecompose.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.flexcode.wedatecompose.data.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

open class BaseViewModel(private val logService: LogService): ViewModel() {
    fun launchCatching(snackBar:Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackBar){
                    SnackBarManager.showMessage(throwable.toSnackbarMessage())
                    Log.d("ERRORR_HAPENNED :: ","${throwable.message} ${throwable.localizedMessage}")
                }
                logService.logNonFatalCrash(throwable)
                Log.d("ERRORR_HAPENNED :: ","${throwable.message} ${throwable.localizedMessage}")

            },
            block = block
        )
}