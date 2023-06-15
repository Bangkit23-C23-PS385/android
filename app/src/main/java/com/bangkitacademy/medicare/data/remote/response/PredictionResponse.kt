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

	@field:SerializedName("precaution_2")
	val precaution2: String,

	@field:SerializedName("disease")
	val disease: String,

	@field:SerializedName("precaution_1")
	val precaution1: String,

	@field:SerializedName("precaution_4")
	val precaution4: String,

	@field:SerializedName("precaution_3")
	val precaution3: String,

	@field:SerializedName("description")
	val description: String
)
