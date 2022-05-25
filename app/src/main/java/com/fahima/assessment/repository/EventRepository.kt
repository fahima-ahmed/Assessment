package com.fahima.assessment.repository

import com.fahima.assessment.api.RetrofitInstance
import com.fahima.assessment.model.Event
import retrofit2.Response

class EventRepository {
    suspend fun getEvents(): Response<List<Event>> {
        return RetrofitInstance.api.getEvents()
    }

}