package com.example.frontend.model

import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime

data class MeetupModel(
    val title: String,
    val description: String,
    val userIds: List<Long>,
    val meetAt: LocalDateTime,
    val isPublic: Boolean,
    val placeId: Long
)