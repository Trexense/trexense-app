package com.example.trexense.data.response

import com.google.gson.annotations.SerializedName

data class HotelDetailItemResponse(

	@field:SerializedName("data")
	val data: DataDetailHotel? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class HotelFacility(

	@field:SerializedName("parking")
	val parking: Int? = null,

	@field:SerializedName("aesthetic")
	val aesthetic: Int? = null,

	@field:SerializedName("wifi")
	val wifi: Int? = null,

	@field:SerializedName("cozy")
	val cozy: Int? = null,

	@field:SerializedName("niceView")
	val niceView: Int? = null,

	@field:SerializedName("spa")
	val spa: Int? = null,

	@field:SerializedName("laundry")
	val laundry: Int? = null,

	@field:SerializedName("pool")
	val pool: Int? = null,

	@field:SerializedName("gym")
	val gym: Int? = null,

	@field:SerializedName("breakfast")
	val breakfast: Int? = null
)

data class DataDetailHotel(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("cost")
	val cost: Int? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("hotel")
	val hotel: HotelFacility? = null,

	val filteredFacilites: List<String> = emptyList(),

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("hotelId")
	val hotelId: String? = null
)
