package com.example.frontend.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frontend.model.UserWithLocationModel
import com.example.frontend.ui.component.BottomBar
import com.example.frontend.ui.theme.FrontendTheme
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlin.random.Random

@Composable
fun MapUI(
    currentLocation: LatLng?,
    friends: List<UserWithLocationModel>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    MaterialTheme {
        Column {
            // The map takes up all the space minus the bottom bar
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                currentLocation?.let { location ->
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(location, 15f)
                    }

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxSize(),
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker(
                            state = MarkerState(position = location),
                            title = "Current Location",
                            snippet = "You are here"
                        )

                        // Friends location markers
                        friends.forEach { friend ->
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        friend.latitude,
                                        friend.longitude
                                    )
                                ),
                                title = friend.name,
                                snippet = friend.email,
                                icon = BitmapDescriptorFactory.defaultMarker(Random.nextFloat() * 360)
                            )
                        }
                    }
                }
                FloatingActionButton(
                    onClick = { onClick() },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(50.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
            }

            // Bottom bar at the bottom
            BottomBar(currentLocation)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapUIPreview() {
    FrontendTheme {
        MapUI(LatLng(1.35, 103.87), emptyList(), onClick = {})
    }
}
