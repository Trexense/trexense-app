package com.example.trexense.view.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trexense.data.response.RegisterResponse
import com.example.trexense.data.retrofit.ApiConfig
import com.example.trexense.data.utils.Alert
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Alert<String>>()
    val snackBarText: LiveData<Alert<String>> = _snackBarText

    private val _isRegisterSuccess = MutableLiveData<Boolean>()
    val isRegisterSuccess: LiveData<Boolean> = _isRegisterSuccess

    fun userRegister(username: String, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().createFormRegister(username, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${responseBody}")
                    _snackBarText.value = Alert(responseBody?.message.toString())
                    _isRegisterSuccess.value = true
                } else {
                    val errorBody = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        _isRegisterSuccess.value = false
                        val errorResponse = gson.fromJson(errorBody, RegisterResponse::class.java)
                        val errorMessage = errorResponse.message
                        Log.e(TAG, "Error Message: ${errorMessage}")
                        _snackBarText.value = Alert(errorMessage.toString())
                    } catch (e: JsonSyntaxException) {
                        _isRegisterSuccess.value = false
                        Log.e(TAG, "Error parsing JSON: ${e.message}")
                    }
                }

            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                _isRegisterSuccess.value = false
                Log.e(TAG, "OnFailure ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }
}