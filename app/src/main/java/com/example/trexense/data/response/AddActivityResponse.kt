package com.example.trexense.data.response

import com.google.gson.annotations.SerializedName

data class AddActivityResponse(

	@field:SerializedName("data")
	val data: DataAddActivity,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataAddActivity(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("cost")
	val cost: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("planDetailId")
	val planDetailId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
