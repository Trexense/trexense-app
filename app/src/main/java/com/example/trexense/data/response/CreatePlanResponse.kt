package com.example.trexense.data.response

import com.google.gson.annotations.SerializedName

data class CreatePlanResponse(

	@field:SerializedName("data")
	val data: DataCreate,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataCreate(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("endDate")
	val endDate: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("startDate")
	val startDate: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
