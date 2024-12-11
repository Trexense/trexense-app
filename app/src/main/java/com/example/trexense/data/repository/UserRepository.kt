package com.example.trexense.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.example.trexense.data.pref.UserModel
import com.example.trexense.data.pref.UserPreference
import com.example.trexense.data.response.UserResponse
import com.example.trexense.data.retrofit.ApiConfig
import com.example.trexense.data.retrofit.ApiService
import com.example.trexense.data.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun updateProfile(
        idUser: String,
        name: String,
        email: String
    ): Result<UserResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.updateUser(idUser,name, email)
                Log.i("UserRepository", "response status: $response ")
                if (response.status == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            }catch (e: Exception) {
                Log.e("UserRepository ", "${e.message}", )
                Result.Error(e.message.toString())
            }
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(userPreference: UserPreference, apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}