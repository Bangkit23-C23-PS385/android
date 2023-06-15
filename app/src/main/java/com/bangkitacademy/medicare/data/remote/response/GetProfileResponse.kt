package com.bangkitacademy.medicare.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetProfileResponse(

    @field:SerializedName("data")
    val data: ProfileData? = null,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int
)

@Parcelize
data class ProfileData(

    @field:SerializedName("DateOfBirth")
    val dateOfBirth: String,

    @field:SerializedName("UserId")
    val userId: String,

    @field:SerializedName("CreatedAt")
    val createdAt: String,

    @field:SerializedName("Height")
    val height: Int,

    @field:SerializedName("Gender")
    val gender: String,

    @field:SerializedName("UpdatedAt")
    val updatedAt: String,

    @field:SerializedName("Weight")
    val weight: Int,

    @field:SerializedName("Name")
    val name: String
) : Parcelable
