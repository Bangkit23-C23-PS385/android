package com.bangkitacademy.medicare.di

import android.content.Context
import com.bangkitacademy.medicare.data.remote.retrofitpublic.PublicApiConfig
import com.bangkitacademy.medicare.repository.NewsRepository

object Injection {

    fun provideNewsRepository(context: Context): NewsRepository {
        val publicApiService = PublicApiConfig.getPublicApiService()
        return NewsRepository.getInstance(publicApiService)
    }
}