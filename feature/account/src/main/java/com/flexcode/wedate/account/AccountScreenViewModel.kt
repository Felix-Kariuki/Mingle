package com.flexcode.wedate.account

import com.flexcode.wedate.common.BaseViewModel
import com.flexcode.wedate.common.data.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModel @Inject constructor(
    logService: LogService
):BaseViewModel(logService){

}