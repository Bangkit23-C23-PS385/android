package com.bangkitacademy.medicare.data.remote.retrofit

import com.bangkitacademy.medicare.data.remote.request.CreateProfileRequest
import com.bangkitacademy.medicare.data.remote.request.LoginRequest
import com.bangkitacademy.medicare.data.remote.request.PredictionRequest
import com.bangkitacademy.medicare.data.remote.request.RegisterRequest
import com.bangkitacademy.medicare.data.remote.request.ResendRequest
import com.bangkitacademy.medicare.data.remote.request.UpdateProfileRequest
import com.bangkitacademy.medicare.data.remote.response.GetProfileResponse
import com.bangkitacademy.medicare.data.remote.response.LoginResponse
import com.bangkitacademy.medicare.data.remote.response.PredictionResponse
import com.bangkitacademy.medicare.data.remote.response.RegisterResponse
import com.bangkitacademy.medicare.data.remote.response.ResendResponse
import com.bangkitacademy.medicare.data.remote.response.SymptomsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("v1/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @POST("v1/register")
    suspend fun register(@Body body: RegisterRequest): RegisterResponse

    @POST("v1/predict")
    suspend fun predict(@Body body: PredictionRequest): PredictionResponse

    @GET("v1/symptoms")
    suspend fun getSymptoms() : SymptomsResponse

    @POST("v1/resend")
    suspend fun resend(@Body body: ResendRequest): ResendResponse

    @POST("v1/profile")
    suspend fun createProfile(@Body body: CreateProfileRequest) : GetProfileResponse

    @GET("v1/profile")
    suspend fun getProfile() : GetProfileResponse

    @PUT("v1/profile")
    suspend fun updateProfile(@Body body: UpdateProfileRequest) : GetProfileResponse


}