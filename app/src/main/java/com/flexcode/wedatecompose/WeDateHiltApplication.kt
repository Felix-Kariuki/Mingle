package com.flexcode.wedatecompose

import android.app.Application
import com.flexcode.wedatecompose.crashlyics.CrashlyticsTree
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WeDateHiltApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        Firebase.database.setPersistenceEnabled(true)
    }

    private fun initTimber() = when{
        BuildConfig.DEBUG -> {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String?{
                    return super.createStackElementTag(element) + ":" + element.lineNumber
                }
            })
        }else -> {
            Timber.plant(CrashlyticsTree())
        }
    }
}