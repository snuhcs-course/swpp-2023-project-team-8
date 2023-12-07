package com.example.frontend.model

import java.time.LocalDateTime

data class MeetUpResponse(
    val title: String,
    val description: String,
    val meetAt: LocalDateTime,
    val users: List<UserModel>,
    val place: PlaceModel
)