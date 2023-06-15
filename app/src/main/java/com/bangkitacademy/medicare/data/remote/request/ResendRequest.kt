package com.bangkitacademy.medicare.data.remote.request

import com.google.gson.annotations.SerializedName

data class ResendRequest(

	@field:SerializedName("email")
	val email: String? = null
)
