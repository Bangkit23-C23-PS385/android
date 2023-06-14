package com.bangkitacademy.medicare.data.remote.response

import com.google.gson.annotations.SerializedName

data class SymptomsResponse(

	@field:SerializedName("data")
	val data: DataSymptoms,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class SymptomsItem(

	@field:SerializedName("symptom_id")
	val symptomId: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("symptom_en")
	val symptomEn: String
)

data class DataSymptoms(

	@field:SerializedName("symptoms")
	val symptoms: List<SymptomsItem>
)
