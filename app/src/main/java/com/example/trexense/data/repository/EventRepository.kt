package com.example.trexense.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.trexense.data.EventPagingSource
import com.example.trexense.data.pref.UserPreference
import com.example.trexense.data.response.CreatePlanResponse
import com.example.trexense.data.response.DataItem
import com.example.trexense.data.response.EventResponse
import com.example.trexense.data.response.PlansResponse
import com.example.trexense.data.retrofit.ApiService
import com.example.trexense.data.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class EventRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun getEvent(): EventResponse {
        try {
            userPreference.getSession().firstOrNull()?.token
                ?: throw NullPointerException("Token is null")
            return apiService.getEvent()
        }catch (e: Exception) {
            throw e
        }
    }

    fun getEventPage(): Flow<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { EventPagingSource(apiService) }
        ).flow
    }


    suspend fun getPlans(): Result<PlansResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPlans()
                if (response.status == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: Exception) {
                Result.Error(e.message.toString())
            }
        }
    }

    suspend fun createPlan(
        name: String,
        startDate: String,
        endDate: String
    ): Result<CreatePlanResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.createPlan(name, startDate, endDate)
                if (response.status == 200) {
                    Result.Success(response)
                } else {
                    Result.Error(response.message)
                }
            } catch (e: Exception) {
                Result.Error(e.message.toString())
            }
        }
    }

    companion object {
        fun getInstance(apiService: ApiService, userPreference: UserPreference) : EventRepository {
            return EventRepository(apiService, userPreference)
        }
     }
}