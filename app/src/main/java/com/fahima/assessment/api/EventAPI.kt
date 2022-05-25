package com.fahima.assessment.api

import com.fahima.assessment.model.Event
import retrofit2.Response
import retrofit2.http.GET

interface EventAPI {

    @GET("phunware-services/dev-interview-homework/master/feed.json")
    suspend fun getEvents(): Response<List<Event>>

}