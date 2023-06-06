package com.bangkitacademy.medicare.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkitacademy.medicare.data.preferences.UserPreference
import com.bangkitacademy.medicare.data.remote.response.GetUserResponse
import com.bangkitacademy.medicare.data.remote.retrofit.ApiService
import com.bangkitacademy.medicare.utils.Result

class AppRepository private constructor(private val apiService: ApiService, private val pref: UserPreference) {

    fun getUser(id: String): LiveData<Result<GetUserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser(id)
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreference
        ): AppRepository =
            instance ?: synchronized(this) {
                instance ?: AppRepository(apiService, pref)
            }.also { instance = it }
    }
}