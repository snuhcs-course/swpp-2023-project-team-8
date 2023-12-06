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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.frontend.ui.map.FriendsMapUI
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.usecase.CheckInUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient
    var currentLocation by mutableStateOf<LatLng?>(null)

    @Inject
    lateinit var checkInUseCase: CheckInUseCase

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

        currentLocation?.let {
            checkInUseCase.execute(it.latitude, it.longitude)
        }
    }

    override fun onResume() {
        super.onResume()

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

