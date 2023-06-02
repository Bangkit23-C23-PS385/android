package com.bangkitacademy.medicare.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.bangkitacademy.medicare.data.pagingdata.NewsPagingSource
import com.bangkitacademy.medicare.data.remote.response.ArticlesItem
import com.bangkitacademy.medicare.data.remote.response.NewsResponse
import com.bangkitacademy.medicare.data.remote.retrofitpublic.PublicApiService
import com.bangkitacademy.medicare.utils.Result

class NewsRepository private constructor(private val publicApiService: PublicApiService) {
    fun getNews(): LiveData<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = {
                NewsPagingSource(publicApiService)
            }
        ).liveData
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(publicApiService: PublicApiService): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(publicApiService).also { instance = it }
            }
    }
}