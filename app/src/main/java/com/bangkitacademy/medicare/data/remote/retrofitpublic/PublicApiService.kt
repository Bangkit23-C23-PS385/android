package com.bangkitacademy.medicare.data.remote.retrofitpublic

import com.bangkitacademy.medicare.data.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PublicApiService {
    @GET("top-headlines?country=id&category=health")
    suspend fun getNews(
        @Query("pageSize") pageSize: Int = 100,
        @Query("page") page: Int,
    ): NewsResponse
}