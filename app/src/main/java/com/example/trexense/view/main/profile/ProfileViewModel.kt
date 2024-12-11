package com.example.trexense.view.main.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.trexense.data.pref.UserModel
import com.example.trexense.data.repository.UserRepository
import com.example.trexense.data.response.DataUserProfile
import com.example.trexense.data.response.UserResponse
import com.example.trexense.data.utils.Result
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    private val _isLoadingProfile = MutableLiveData<Boolean>()
    val isLoadingProfile:LiveData<Boolean> = _isLoadingProfile

    private val _updateUser = MutableLiveData<Result<UserResponse>>()
    val updateUser : LiveData<Result<UserResponse>> = _updateUser

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun userUpdate(
        idUser: String,
        name: String,
        email: String){
        _isLoadingProfile.value = true
        viewModelScope.launch {
            repository.updateProfile(idUser, name, email).let {
                _updateUser.value = it
                _isLoadingProfile.value = false
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

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