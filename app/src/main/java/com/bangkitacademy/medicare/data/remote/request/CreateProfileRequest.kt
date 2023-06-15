package com.bangkitacademy.medicare.data.remote.request

import com.google.gson.annotations.SerializedName

data class CreateProfileRequest(

	@field:SerializedName("name")
	val name: String? = null,
)
