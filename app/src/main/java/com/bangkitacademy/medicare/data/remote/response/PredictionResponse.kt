package com.bangkitacademy.medicare.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("data")
	val data: DataResult,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataResult(

	@field:SerializedName("disease")
	val disease: String
)
