package com.example.trexense.view.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.trexense.data.pref.UserModel
import com.example.trexense.data.pref.UserPreference
import com.example.trexense.data.repository.HotelRepository
import com.example.trexense.data.repository.UserRepository
import com.example.trexense.data.response.DataDetailHotel
import com.example.trexense.data.response.HotelDetailItemResponse
import com.example.trexense.data.response.HotelResponse
import com.example.trexense.data.retrofit.ApiConfig
import com.example.trexense.data.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelViewModel(
    private val userRepository: UserRepository,
    private val hotelRepository: HotelRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _dataHotel = MutableLiveData<Result<HotelResponse>>()
    val dataHotel: LiveData<Result<HotelResponse>> = _dataHotel

    private val _detailDataHotel = MutableLiveData<Result<HotelDetailItemResponse>>()
    val detailDataHotel: LiveData<Result<HotelDetailItemResponse>> = _detailDataHotel

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun getHotelRecommendation(): LiveData<Result<HotelResponse>> =
        hotelRepository.getHotelRecommendation()

    fun getDetailDataHotel(id: String): LiveData<Result<HotelDetailItemResponse>> = hotelRepository.getHotelDetailData(id)

}