package com.fahima.assessment.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Event(
    val id: Int? = null,
    val description: String? = null,
    val title: String? = null,
    val timestamp: String? = null,
    val image: String? = null,
    val phone: String? = null,
    val date: String? = null,
    @SerializedName("locationline1")
    val locationLine1: String? = null,
    @SerializedName("locationline2")
    val locationLine2: String? = null
) : Serializable