package com.example.trexense.data.response

import com.google.gson.annotations.SerializedName

data class PlansResponse(

	@field:SerializedName("data")
	val data: List<DataPlans>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataPlans(

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
