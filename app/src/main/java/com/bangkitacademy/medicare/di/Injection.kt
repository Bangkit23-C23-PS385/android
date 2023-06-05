package com.bangkitacademy.medicare.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bangkitacademy.medicare.data.preferences.UserPreference
import com.bangkitacademy.medicare.data.remote.retrofit.ApiConfig
import com.bangkitacademy.medicare.data.remote.retrofitpublic.PublicApiConfig
import com.bangkitacademy.medicare.repository.AuthenticationRepository
import com.bangkitacademy.medicare.repository.NewsRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
object Injection {
    fun provideNewsRepository(context: Context): NewsRepository {
        val publicApiService = PublicApiConfig.getPublicApiService()
        return NewsRepository.getInstance(publicApiService)
    }

    fun providePreferences(context: Context): UserPreference {
        return UserPreference.getInstance(context.dataStore)
    }

    fun authenticationRepository(context: Context): AuthenticationRepository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return AuthenticationRepository.getInstance(apiService, pref)
    }
}