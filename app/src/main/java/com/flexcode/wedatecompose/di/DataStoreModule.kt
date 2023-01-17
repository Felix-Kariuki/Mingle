package com.flexcode.wedatecompose.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.flexcode.wedatecompose.common.utils.Constants.AUTH_PREFERENCES
import com.flexcode.wedatecompose.data.local.datastore.AuthDataStore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferenceDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(AUTH_PREFERENCES)
            }
        )
    }

    @Provides
    @Singleton
    fun provideAuthPreferences(dataStore: DataStore<Preferences>, gson: Gson) =
        AuthDataStore(dataStore, gson)

    @Provides
    @Singleton
    fun provideGson() = Gson()

}