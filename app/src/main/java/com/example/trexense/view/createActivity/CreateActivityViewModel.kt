package com.example.trexense.view.createActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trexense.data.repository.EventRepository
import com.example.trexense.data.response.AddActivityResponse
import com.example.trexense.data.utils.Result
import kotlinx.coroutines.launch

class CreateActivityViewModel(private val repository: EventRepository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _addResult = MutableLiveData<Result<AddActivityResponse>>()
    val addResult: MutableLiveData<Result<AddActivityResponse>> = _addResult

    fun addActivity(
        day_id: String,
        name: String,
        location: String,
        cost: String,
        description: String
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.addActivity(day_id, name, location, cost, description)
            _addResult.value = result
            _isLoading.value = false
        }
    }
}