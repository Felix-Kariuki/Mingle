package com.flexcode.wedate.common.data

interface LogService {
    fun logNonFatalCrash(throwable:Throwable)
}