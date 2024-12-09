package com.example.trexense.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.trexense.data.EventPagingSource
import com.example.trexense.data.pref.UserPreference
import com.example.trexense.data.response.DataItem
import com.example.trexense.data.response.EventResponse
import com.example.trexense.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

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

    companion object {
        fun getInstance(apiService: ApiService, userPreference: UserPreference) : EventRepository {
            return EventRepository(apiService, userPreference)
        }
     }
}