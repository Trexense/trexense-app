package com.example.trexense.view.main.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trexense.data.repository.EventRepository
import com.example.trexense.data.response.PlansResponse
import com.example.trexense.data.response.SaveHotelResponse
import com.example.trexense.data.utils.Result
import kotlinx.coroutines.launch

class PlanViewModel(private val repository: EventRepository): ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _planResult = MutableLiveData<Result<PlansResponse>>()
    val plansResult: MutableLiveData<Result<PlansResponse>> = _planResult

    private val _saveResult = MutableLiveData<Result<SaveHotelResponse>>()
    val saveResult: MutableLiveData<Result<SaveHotelResponse>> = _saveResult
    fun getPlans(){
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.getPlans()
            _planResult.value = if (result is Result.Success && result.data.data.isEmpty()) {
                Result.Error("data kosong")
            } else {
                result
            }
            _isLoading.value = false
        }
    }

    fun saveHotel(day_id: String, hotel_id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.saveHotel(day_id, hotel_id)
            _saveResult.value = result
            _isLoading.value = false
        }
    }

}