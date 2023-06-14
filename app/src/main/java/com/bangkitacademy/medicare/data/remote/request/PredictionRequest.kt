package com.bangkitacademy.medicare.data.remote.request

import com.google.gson.annotations.SerializedName

data class PredictionRequest(

	@field:SerializedName("symptoms")
	val symptoms: List<String?>? = null
)
