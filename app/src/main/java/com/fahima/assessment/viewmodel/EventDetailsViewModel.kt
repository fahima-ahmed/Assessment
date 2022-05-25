package com.fahima.assessment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahima.assessment.model.Event
import com.fahima.assessment.util.Resource

class EventDetailsViewModel: ViewModel() {

    val eventDetailData: MutableLiveData<Event> = MutableLiveData()

    fun setEventData(event: Event) {
        eventDetailData.value = event
    }
}