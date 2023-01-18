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