package com.example.trexense.view.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trexense.data.repository.EventRepository
import com.example.trexense.data.response.SearchResponse
import com.example.trexense.data.utils.Result
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: EventRepository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchResult = MutableLiveData<Result<SearchResponse>>()
    val searchResult: MutableLiveData<Result<SearchResponse>> = _searchResult

    fun searchHotel(name: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.searchHotel(name)
            _searchResult.value = result
            _isLoading.value = false
        }
    }
}