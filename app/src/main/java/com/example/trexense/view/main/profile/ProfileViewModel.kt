package com.example.trexense.view.main.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trexense.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    fun logout() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _isLoading.value = false
                repository.logout()

            }catch (e: Exception) {
                _isLoading.value = false
                Log.d("ProfileViewMode", "logout: ${e.message} ")
            }
        }
    }
}