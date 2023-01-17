package com.flexcode.wedatecompose.data

interface LogService {
    fun logNonFatalCrash(throwable:Throwable)
}