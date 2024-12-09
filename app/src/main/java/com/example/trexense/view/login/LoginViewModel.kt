package com.example.trexense.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trexense.data.pref.UserModel
import com.example.trexense.data.repository.UserRepository
import com.example.trexense.data.response.LoginResponse
import com.example.trexense.data.retrofit.ApiConfig
import com.example.trexense.data.utils.Alert
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Alert<String>>()
    val snackBarText: LiveData<Alert<String>> = _snackBarText

    private val _nameUser = MutableLiveData<String?>()
    val nameUser: LiveData<String?> = _nameUser

    private val _isToken = MutableLiveData<String?>()
    val isToken: LiveData<String?> = _isToken

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean> = _isLoginSuccess

    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        _isLoginSuccess.value = false
        val client = ApiConfig.getApiService().formLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e(TAG, " Response: Token ${responseBody.tokens?.access}, Nama User : ${responseBody.data?.name} ")
                    _nameUser.value = responseBody.data?.name
                    _isToken.value = responseBody.tokens?.access
                    _isLoginSuccess.value = true
                    _snackBarText.value = Alert(responseBody.message.toString())
                }else {
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson(errorBody, LoginResponse::class.java)
                        val errorMessage = errorResponse.message
                        Log.e(TAG, "Error Message: $errorMessage") // Menampilkan pesan error di log
                        _snackBarText.value = Alert(errorMessage.toString()) // Menampilkan pesan error di Snackbar
                        _isLoginSuccess.value = false
                    } catch (e: JsonSyntaxException) {
                        _isLoginSuccess.value = false
                        Log.e(TAG, "Error parsing JSON: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _isLoginSuccess.value = false
                Log.e(TAG, "ERROR : ${t.message}")
            }

        })
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"

    }
}