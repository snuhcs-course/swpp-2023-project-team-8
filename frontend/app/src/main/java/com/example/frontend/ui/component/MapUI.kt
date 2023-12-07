package com.example.frontend.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.frontend.data.defaultLocationMarkers
import com.example.frontend.model.UserWithLocationModel
import com.example.frontend.ui.theme.FrontendTheme
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import kotlin.random.Random

@Composable
fun MapWithMarker(
    currentLocation: LatLng?,
    friends: List<UserWithLocationModel>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        currentLocation?.let { location ->
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(location, 15f)
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Polygon(
                    points = defaultLocationMarkers,
                    fillColor = Color(0x89CFF0FF)
                )
                Marker(
                    state = MarkerState(position = location),
                    title = "Current Location",
                    snippet = "You are here"
                )

                friends.forEach { buildMarkerIcon(it) }
            }
        }
    }
}

@Composable
private fun buildMarkerIcon(user: UserWithLocationModel) {
    return Marker(
        state = MarkerState(
            position = LatLng(
                user.latitude,
                user.longitude
            )
        ),
        title = user.name,
        snippet = user.email,
        icon = BitmapDescriptorFactory.defaultMarker(Random.nextFloat() * 360)
    )
}

@Preview(showBackground = true)
@Composable
private fun MapUIPreview() {
    val friends = listOf(
        UserWithLocationModel(
            id = 1,
            name = "김철수",
            email = "a",
            latitude = 1.35,
            longitude = 103.88
        ),
        UserWithLocationModel(
            id = 2,
            name = "김영희",
            email = "b",
            latitude = 1.36,
            longitude = 103.89
        ),
    )
    FrontendTheme {
        MapWithMarker(
            LatLng(1.35, 103.87),
            friends
        )
    }
}
