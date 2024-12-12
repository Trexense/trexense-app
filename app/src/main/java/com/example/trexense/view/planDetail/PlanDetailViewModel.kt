package com.example.trexense.view.planDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trexense.data.repository.EventRepository
import com.example.trexense.data.response.PlanDetailResponse
import com.example.trexense.data.utils.Result
import kotlinx.coroutines.launch

class PlanDetailViewModel(private val repository: EventRepository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _planDetailResult = MutableLiveData<Result<PlanDetailResponse>>()
    val planDetailResult: MutableLiveData<Result<PlanDetailResponse>> = _planDetailResult

    fun getDetailPlan(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.getDetailPlan(id)
            _planDetailResult.value = result
            _isLoading.value = false
        }
    }
}