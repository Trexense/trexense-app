package com.example.trexense.data.repository

import com.example.trexense.data.models.ChatRequest
import com.example.trexense.data.pref.UserPreference
import com.example.trexense.data.response.ChatResponse
import com.example.trexense.data.retrofit.ApiConfig
import com.example.trexense.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.trexense.data.utils.Result
import kotlinx.coroutines.flow.firstOrNull

class ChatRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    companion object {
        fun getInstance(apiService: ApiService, userPreference: UserPreference): ChatRepository {
            return ChatRepository(apiService, userPreference)
        }
    }

    suspend fun getChatResponse(message: String): Result<ChatResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val token = userPreference.getSession().firstOrNull()?.token
                    ?: throw NullPointerException("Token is null")
                val response = ApiConfig.getApiService(token).SendChat(message)
                if (response.status == 200) {
                    Result.Success(response)
                } else {
                    Result.Error("No reply received from server")
                }
            } catch (e: Exception) {
                Result.Error(e.message.toString())
            }
        }
    }
}
