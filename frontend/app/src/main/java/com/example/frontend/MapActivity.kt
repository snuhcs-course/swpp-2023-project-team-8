package com.example.frontend

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontend.model.UserWithLocationModel
import com.example.frontend.repository.FriendsViewModel
import com.example.frontend.ui.component.BottomBar
import com.example.frontend.ui.theme.FrontendTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.random.Random


@AndroidEntryPoint
class MapActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var currentLocation by mutableStateOf<LatLng?>(null)

    private fun hasBackgroundLocationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            // Background location access is not needed or is granted by default on devices running OS versions prior to Android 11
            true
        }
    }

    private fun showPermissionExplanation() {
        // Update your UI to show the explanation message
        setContent {
            FrontendTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AlertDialog(
                        onDismissRequest = {},
                        title = { Text("백그라운드 위치 권한 허용 요청") },
                        text = {
                            Text(
                                "저희 앱은 백그라운드 위치 권한을 필요로 합니다. " +
                                        "권한->위치->항상 허용"
                            )
                        },
                        confirmButton = {
                            Button(onClick = {
                                openAppSettings()
                            }) {
                                Text("설정")
                            }
                        }
                    )
                }
            }
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            FrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FriendsMapUI(currentLocation, onClick = {
                        val intent = Intent(this, MeetupActivity::class.java)
                        intent.putExtra("currentLocation", currentLocation)
                        this.startActivity(intent)
                    })
                }
            }
        }

        checkAndRequestLocationPermissions()
    }

    override fun onResume() {
        super.onResume()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            FrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FriendsMapUI(currentLocation, onClick = {
                        val intent = Intent(this, MeetupActivity::class.java)
                        this.startActivity(intent)
                    })
                }
            }
        }

        checkAndRequestLocationPermissions()
    }

    private fun checkAndRequestLocationPermissions() {
        when {
            hasBackgroundLocationPermission(this) -> {
                // Background location access granted.
                requestLocationUpdates()
            }

            else -> {
                // Show a UI to explain why the permission is needed.
                showPermissionExplanation()
            }
        }
    }


    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.locations.firstOrNull()?.let {
                        currentLocation = LatLng(it.latitude, it.longitude)
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }


        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.firstOrNull()?.let {
                    currentLocation = LatLng(it.latitude, it.longitude)
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}

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

            }

        }
    }
}

@Composable
fun FriendsMapUI(currentLocation: LatLng?, onClick: () -> Unit) {
    val viewModel: FriendsViewModel = viewModel()
    val friendsList by viewModel.friendsList.observeAsState(emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        while (isActive) {
            viewModel.fetchFriends()
            delay(3000)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            BottomBar(currentLocation)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            MapUI(currentLocation, friendsList, onClick = { onClick() }, modifier = Modifier.fillMaxSize())
            FloatingActionButton(
                onClick = { onClick() },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }

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

