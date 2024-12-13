package com.example.trexense.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.example.trexense.data.pref.UserPreference
import com.example.trexense.data.response.HotelDetailItemResponse
import com.example.trexense.data.response.HotelFacility
import com.example.trexense.data.response.PlanDetailResponse
import com.example.trexense.data.retrofit.ApiConfig
import com.example.trexense.data.retrofit.ApiService
import com.example.trexense.data.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class HotelRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun getHotelRecommendation() = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getSession().firstOrNull()?.token
                ?: throw NullPointerException("Token is null")
            withContext(Dispatchers.IO) {
                val response = ApiConfig.getApiService(token).getHotelRecomendation()
                Log.d("HotelRepo", "data response hotel : $response ")
                if (response.status == 200) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Error(response.message.toString()))
                }
            }
        } catch (e: Exception) {
            Log.e("HotelRepo", "getError : ${e.message}", e)
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getHotelDetailData(idHotel: String) = liveData {
        emit(Result.Loading)
        try {
            val token = userPreference.getSession().firstOrNull()?.token
                ?: throw NullPointerException("Token is null")
            withContext(Dispatchers.IO) {
                emit(Result.Loading)
                val response = ApiConfig.getApiService(token).getDetailHotel(idHotel)
                if (response.status == 200) {
                    emit(Result.Success(response.copy(data = response.data?.copy(filteredFacilites = filterFacilites(response.data.hotel!!)))))
                } else {
                    emit(Result.Error(response.message.toString()))
                }
            }
        } catch (e: Exception) {
            Log.e("HotelRepo", "getError : ${e.message}", e)
            emit(Result.Error(e.message.toString()))
        }
    }

    private fun filterFacilites(hotel: HotelFacility) : List<String> {
        val filteredFacilites = mutableListOf<String>()
        if (hotel.cozy == 1) {
            filteredFacilites.add("Cozy")
        }
        if(hotel.parking == 1) {
            filteredFacilites.add("Parking")
        }
        if(hotel.gym == 1) {
            filteredFacilites.add("Gym")
        }
        if(hotel.pool == 1) {
            filteredFacilites.add("Pool")
        }
        if(hotel.spa == 1) {
            filteredFacilites.add("Spa")
        }
        if(hotel.aesthetic == 1) {
            filteredFacilites.add("Aesthetic")
        }
        if(hotel.breakfast == 1) {
            filteredFacilites.add("Breakfast")
        }
        if(hotel.laundry == 1) {
            filteredFacilites.add("Laundry")
        }
        if(hotel.niceView == 1) {
            filteredFacilites.add("Nice View")
        }

        return filteredFacilites

    }


    companion object {
        fun getInstance(apiService: ApiService, userPreference: UserPreference):
                HotelRepository {
            return HotelRepository(apiService, userPreference)
        }
    }
}