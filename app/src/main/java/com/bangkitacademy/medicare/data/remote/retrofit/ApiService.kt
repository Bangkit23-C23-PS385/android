package com.bangkitacademy.medicare.data.remote.retrofit

import com.bangkitacademy.medicare.data.remote.request.LoginRequest
import com.bangkitacademy.medicare.data.remote.request.RegisterRequest
import com.bangkitacademy.medicare.data.remote.response.LoginResponse
import com.bangkitacademy.medicare.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body body: RegisterRequest): RegisterResponse
}