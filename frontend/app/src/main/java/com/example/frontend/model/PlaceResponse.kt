package com.example.frontend.model

import com.google.android.gms.maps.model.LatLng

data class PlaceResponse(
    var location: LatLng?,
    val name: String?,
    val id: Long,
    val kind: String
)

data class PlaceResponseWrapper(
    val places: List<PlaceResponse>
)