package com.example.trexense.view.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.example.trexense.data.pref.UserModel
import com.example.trexense.data.repository.EventRepository
import com.example.trexense.data.repository.UserRepository
import com.example.trexense.data.response.DataItem
import kotlinx.coroutines.flow.Flow

class HomeViewModel(
    private val repository: UserRepository,
    private val eventRepository: EventRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _eventList = MutableLiveData<List<DataItem>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    suspend fun getEvents() {
        _isLoading.value = true
        try {
            _isLoading.value = false
            val response = eventRepository.getEvent()
            Log.d(TAG, "getEvents: ${response.data}")
            _eventList.postValue(response.data)
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

    companion object {
        private const val TAG = "HomeViewModel"
    }
}