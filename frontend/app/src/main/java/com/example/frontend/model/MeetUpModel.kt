package com.example.frontend.model

import com.google.android.gms.maps.model.LatLng

data class MeetupModel(
    val title: String,
    val friends: List<String>,
    val time: String,
    val date: String,
    val location: LatLng
)