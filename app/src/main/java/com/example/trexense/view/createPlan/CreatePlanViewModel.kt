package com.example.trexense.view.createPlan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trexense.data.repository.EventRepository
import com.example.trexense.data.response.CreatePlanResponse
import com.example.trexense.data.response.PlansResponse
import com.example.trexense.data.utils.Result
import kotlinx.coroutines.launch

class CreatePlanViewModel(private val repository: EventRepository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _createPlanResult = MutableLiveData<Result<CreatePlanResponse>>()
    val createPlanResult: MutableLiveData<Result<CreatePlanResponse>> = _createPlanResult

    fun createPlan(
        name: String,
        startDate: String,
        endDate: String
    ){
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.createPlan(name, startDate, endDate)
            _createPlanResult.value = result
            _isLoading.value = false
        }
    }
}