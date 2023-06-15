package com.bangkitacademy.medicare.data.remote.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(

	@field:SerializedName("gender")
	val gender: String = "",

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("weight")
	val weight: Int = 0,

	@field:SerializedName("dateOfBirth")
	val dateOfBirth: String = "0001-01-01",

	@field:SerializedName("height")
	val height: Int = 0
)
