package com.example.trexense.data.response

import com.google.gson.annotations.SerializedName

data class PlanDetailResponse(

	@field:SerializedName("data")
	val data: PlanDetailData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class PlanDetailsItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("activities")
	val activities: List<ActivitiesItem>,

	@field:SerializedName("hotel")
	val hotel: List<HotelItem>,

	@field:SerializedName("planId")
	val planId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("day")
	val day: Int
)

data class HotelDetail(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("cost")
	val cost: Int,

	@field:SerializedName("imageUrl")
	val imageUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("hotelId")
	val hotelId: String
)

data class HotelItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("hotelDetailId")
	val hotelDetailId: String,

	@field:SerializedName("planDetailId")
	val planDetailId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("hotelDetail")
	val hotelDetail: HotelDetail,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class PlanDetailData(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("planDetails")
	val planDetails: List<PlanDetailsItem>,

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

data class ActivitiesItem(

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
