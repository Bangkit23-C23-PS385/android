package com.bangkitacademy.medicare.data.remote.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(

	@field:SerializedName("identifier")
	val identifier: String? = null,

	@field:SerializedName("password")
	val password: String? = null
)
