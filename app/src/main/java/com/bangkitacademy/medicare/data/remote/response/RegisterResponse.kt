package com.bangkitacademy.medicare.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: Any? = null,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)
