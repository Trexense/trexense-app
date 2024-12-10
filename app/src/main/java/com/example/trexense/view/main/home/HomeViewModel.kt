package com.example.trexense.view.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.trexense.data.EventPagingSource
import com.example.trexense.data.pref.UserModel
import com.example.trexense.data.repository.EventRepository
import com.example.trexense.data.repository.UserRepository
import com.example.trexense.data.response.DataItem
import com.example.trexense.data.response.EventResponse
import com.example.trexense.data.retrofit.ApiConfig
import com.example.trexense.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val repository: UserRepository,
    private val eventRepository: EventRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _eventList = MutableLiveData<List<DataItem>>()
    val eventList: LiveData<List<DataItem>> = _eventList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _dataevents = MutableLiveData<List<DataItem>>()
    val dataevents: LiveData<List<DataItem>> = _dataevents

//    val listEvent = Pager(PagingConfig(1)) {
//        EventPagingSource(apiService)
//    }.flow.cachedIn(viewModelScope)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    suspend fun getEvents() {
        _isLoading.value = true
        try {
            _isLoading.value = false
            val response = eventRepository.getEvent()
            Log.d(TAG, "getEvents: ${response.data}")
//            _eventList.postValue(response.data)
            _eventList.value = response.data
        } catch (e: Exception) {
            _isLoading.value = false
            throw e
        }
    }

    fun getEventPager(): Flow<PagingData<DataItem>> {
        _isLoading.value = true
        try {
            _isLoading.value = false
            return eventRepository.getEventPager()
        }catch (e: Exception) {
            _isLoading.value = false
            throw e
        }
    }

    fun getListEvent(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiService(token).getEvent(1, 10)
                }
                _isLoading.value = false
                _dataevents.value = response.data
                Log.d(TAG, "getListEvent: ${response.data}")
            }catch (e: Exception) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${e.message}", e)
            }
        }

//        _isLoading.value = true
//        val client = ApiConfig.getApiService(token).getEventList(1, 10)
//        client.enqueue(object : Callback<EventResponse> {
//            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
//                val responseBody = response.body()
//                if (response.isSuccessful) {
//                    _isLoading.value = false
//                    _dataevents.value = responseBody?.data
//                    Log.d(TAG, "onResponse: $responseBody")
//                } else {
//                    Log.d(TAG, "Data events gagal di load")
//                }
//            }
//
//            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//        })
    }



    companion object {
        private const val TAG = "HomeViewModel"
    }
}