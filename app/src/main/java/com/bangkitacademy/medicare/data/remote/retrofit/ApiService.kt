package com.bangkitacademy.medicare.data.remote.retrofit

import com.bangkitacademy.medicare.data.remote.request.LoginRequest
import com.bangkitacademy.medicare.data.remote.request.RegisterRequest
import com.bangkitacademy.medicare.data.remote.response.GetUserResponse
import com.bangkitacademy.medicare.data.remote.response.LoginResponse
import com.bangkitacademy.medicare.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("v1/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @POST("v1/register")
    suspend fun register(@Body body: RegisterRequest): RegisterResponse

    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String
    ) : GetUserResponse
}