package com.bangkitacademy.medicare.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkitacademy.medicare.data.remote.response.GetUserResponse
import com.bangkitacademy.medicare.data.remote.retrofit.ApiService
import com.bangkitacademy.medicare.utils.Result

class AppRepository private constructor(private val apiService: ApiService) {

    fun getUser(id: String): LiveData<Result<GetUserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser(id)
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}