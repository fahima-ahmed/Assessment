package com.fahima.assessment.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fahima.assessment.EventApplication
import com.fahima.assessment.model.Event
import com.fahima.assessment.repository.EventRepository
import com.fahima.assessment.util.Resource
import kotlinx.coroutines.launch
import okio.IOException

class EventViewModel(
    app: Application,
    private val eventRepository: EventRepository
) : AndroidViewModel(app) {

    val events: MutableLiveData<Resource<List<Event>>> = MutableLiveData()

    init {
        getEvents()
    }

    private fun getEvents() = viewModelScope.launch {
        safeEventsCall()
    }

    private suspend fun safeEventsCall() {
        events.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = eventRepository.getEvents()
                events.postValue(Resource.Success(response.body()!!))
            } else {
                events.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> events.postValue(Resource.Error("Network Failure"))
                else -> events.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<EventApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}












