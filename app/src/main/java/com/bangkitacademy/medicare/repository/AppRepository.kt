package com.bangkitacademy.medicare.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkitacademy.medicare.data.preferences.UserPreference
import com.bangkitacademy.medicare.data.remote.request.PredictionRequest
import com.bangkitacademy.medicare.data.remote.response.GetUserResponse
import com.bangkitacademy.medicare.data.remote.response.PredictionResponse
import com.bangkitacademy.medicare.data.remote.response.SymptomsResponse
import com.bangkitacademy.medicare.data.remote.retrofit.ApiService
import com.bangkitacademy.medicare.utils.Result
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

class AppRepository private constructor(private val apiService: ApiService, private val pref: UserPreference) {

    fun getUser(id: String): LiveData<Result<GetUserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser(id)
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun predict(body: PredictionRequest) : LiveData<Result<PredictionResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.predict(body)
            if (response.status.toString().startsWith("4") || response.status.toString().startsWith("5")) {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            val message = if (e is HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                try {
                    JSONObject(errorBody ?: "").getString("message")
                } catch (ex: JSONException) {
                    "Snap, There is something wrong"
                }
            } else {
                e.message.toString()
            }
            emit(Result.Error(message))
        }
    }

    fun getSymptoms() : LiveData<Result<SymptomsResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getSymptoms()
            if (response.status.toString().startsWith("4") || response.status.toString().startsWith("5")) {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            val message = if (e is HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                try {
                    JSONObject(errorBody ?: "").getString("message")
                } catch (ex: JSONException) {
                    "Snap, There is something wrong"
                }
            } else {
                e.message.toString()
            }
            emit(Result.Error(message))
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