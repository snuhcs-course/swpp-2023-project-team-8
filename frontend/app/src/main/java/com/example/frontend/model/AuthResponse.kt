package com.example.frontend.model

import com.google.android.gms.maps.model.LatLng

data class AuthResponse(
    val token: String,
    val userName: String,
    val userCurrentLocation: LatLng?,
    val userMail: String,
    val userProfile: Int
)