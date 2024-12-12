package com.example.trexense.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trexense.data.repository.EventRepository
import com.example.trexense.di.Injection
import com.example.trexense.view.createActivity.CreateActivityViewModel
import com.example.trexense.view.createPlan.CreatePlanViewModel
import com.example.trexense.view.main.plan.PlanViewModel
import com.example.trexense.view.main.search.SearchViewModel
import com.example.trexense.view.planDetail.PlanDetailViewModel

class EventViewModelFactory(private val repository: EventRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PlanViewModel::class.java) -> {
                PlanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CreatePlanViewModel::class.java) -> {
                CreatePlanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PlanDetailViewModel::class.java) -> {
                PlanDetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CreateActivityViewModel::class.java) -> {
                CreateActivityViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: EventViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): EventViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: EventViewModelFactory(Injection.provideEventRepository(context)).also {
                    INSTANCE = it
                }
            }
        }
    }
}
