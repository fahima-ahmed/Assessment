package com.fahima.assessment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fahima.assessment.repository.EventRepository

class EventViewModelProviderFactory(
    private val app: Application,
    private val eventRepository: EventRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventViewModel(app, eventRepository) as T
    }
}