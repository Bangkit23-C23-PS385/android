package com.bangkitacademy.medicare.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkitacademy.medicare.data.preferences.UserModel
import com.bangkitacademy.medicare.data.preferences.UserPreference
import com.bangkitacademy.medicare.data.remote.request.LoginRequest
import com.bangkitacademy.medicare.data.remote.request.RegisterRequest
import com.bangkitacademy.medicare.data.remote.request.ResendRequest
import com.bangkitacademy.medicare.data.remote.response.LoginResponse
import com.bangkitacademy.medicare.data.remote.response.RegisterResponse
import com.bangkitacademy.medicare.data.remote.response.ResendResponse
import com.bangkitacademy.medicare.data.remote.retrofit.ApiConfig
import com.bangkitacademy.medicare.data.remote.retrofit.ApiService
import com.bangkitacademy.medicare.utils.Result
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException


class AuthenticationRepository private constructor(
    private val apiService: ApiService,
    private val pref: UserPreference
) {
    fun login(body: LoginRequest): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(body)
            if (response.status.toString().startsWith("4") || response.status.toString().startsWith("5")) {
                emit(Result.Error(response.message))
            } else {
                val user = UserModel(
                    name = response.data?.name ?: "",
                    token = response.data?.accessToken ?: "",
                    id = response.data?.id ?: "",
                    email = response.data?.email ?: "",
                    username = response.data?.username ?: ""
                )
                pref.saveUser(user)
                ApiConfig.token = response.data?.accessToken?: ""
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

    fun register(body: RegisterRequest) : LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(body)
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

    fun resend(body: ResendRequest) : LiveData<Result<ResendResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.resend(body)
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
        private var instance: AuthenticationRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreference
        ): AuthenticationRepository =
            instance ?: synchronized(this) {
                instance ?: AuthenticationRepository(apiService, pref)
            }.also { instance = it }
    }
}