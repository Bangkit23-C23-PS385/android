package com.bangkitacademy.medicare.data.remote.response

data class GetUserResponse(
    val status: Int,
    val message: String,
    val data: Data,
)

data class Data(
    val id: Int,
    val name: String,
    val email: String,
    val token: String,
)