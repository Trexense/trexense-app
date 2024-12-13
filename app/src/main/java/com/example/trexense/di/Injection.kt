package com.example.trexense.di

import android.content.Context
import com.example.trexense.data.pref.UserPreference
import com.example.trexense.data.pref.dataStore
import com.example.trexense.data.repository.EventRepository
import com.example.trexense.data.repository.HotelRepository
import com.example.trexense.data.repository.UserRepository
import com.example.trexense.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(pref, apiService)
    }

    fun provideEventRepository(context: Context): EventRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return EventRepository.getInstance(apiService, pref)
    }

    fun provideHotelRepository(context: Context) : HotelRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return HotelRepository.getInstance(apiService, pref)
    }

}