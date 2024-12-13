package com.example.trexense.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class HotelResponse(

	@field:SerializedName("data")
	val data: List<DataHotel?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataHotel(

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

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("hotelId")
	val hotelId: String? = null
) : Parcelable
