package com.example.frontend.model

import com.google.android.gms.maps.model.LatLng

data class PlaceModel (
    var location: LatLng?,
    val name: String?
)