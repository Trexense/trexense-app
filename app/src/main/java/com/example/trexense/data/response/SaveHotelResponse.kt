package com.example.trexense.data.response

import com.google.gson.annotations.SerializedName

data class SaveHotelResponse(

	@field:SerializedName("data")
	val data: DataSaveHotel,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataSaveHotel(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("hotelDetailId")
	val hotelDetailId: String,

	@field:SerializedName("planDetailId")
	val planDetailId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
