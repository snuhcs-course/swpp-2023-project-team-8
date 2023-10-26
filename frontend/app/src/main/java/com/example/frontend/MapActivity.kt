package com.example.frontend

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.frontend.ui.theme.FrontendTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.requestLocationUpdates
import com.example.frontend.ui.theme.FrontendTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope


class MapActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation by mutableStateOf<LatLng?>(null)

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
                    MapUI("Android", currentLocation)
                }
            }
        }

        requestLocationUpdates()
    }

    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(1000)

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
}

@Composable
fun MapUI(name: String, currentLocation: LatLng?, modifier: Modifier = Modifier) {

    currentLocation?.let { location ->
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(location, 10f)
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = location),
                title = "Current Location",
                snippet = "You are here"
            )
        }
    }
    // App()
    MaterialTheme {
        Column {
            // Your main content goes here

            Spacer(modifier = Modifier.weight(1f))

            BottomBar()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapUIPreview() {
    FrontendTheme {
        MapUI("Android", LatLng(1.35, 103.87))
    }
}

@Composable
fun IconToggleButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun BottomBar() {
    val icons = listOf(
        Icons.Default.Star,
        Icons.Outlined.AccountCircle,
        Icons.Outlined.CheckCircle,
        Icons.Outlined.Settings
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color(0xFFF3EDF7))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 37.dp, end = 37.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            icons.forEach { icon ->
                IconToggleButton(icon = icon) {
                    // Implement icon button click action here
                    when(icon){
                        icons[0] -> {
                            //

                        }
                        icons[1] -> {
                            //

                        }
                        icons[2] -> {
                            //

                        }
                        icons[3] -> {
                            //

                        }
                    }
                }
            }
        }
    }
}

//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun App() {
//    //checkin이 true일 때 체크인 화면 성공, false일 때 체크인 화면 실패
//    var checkin by remember { mutableStateOf(true) }
//    val scaffoldState : ScaffoldState = rememberScaffoldState()
//    val coroutineScope : CoroutineScope = rememberCoroutineScope()
//
//
//    if(checkin){
//        LaunchedEffect(checkin) {
//            scaffoldState.snackbarHostState.showSnackbar(
//                message = "체크인에 성공했어요! 메인 화면으로 이동해요.",
//                actionLabel = null,
//                duration = SnackbarDuration.Short
//            )
//        }
//    }else{
//        LaunchedEffect(!checkin) {
//            scaffoldState.snackbarHostState.showSnackbar(
//                message = "체크인에 실패했어요!",
//                actionLabel = "재시도",
//                duration = SnackbarDuration.Short
//            )
//        }
//
//    }
//
//
//
//
//    Scaffold(
//        scaffoldState = scaffoldState,
//        modifier = Modifier.offset(y = (-90).dp),
//        content = {
//
//
//
//        }
//    )
//
//
//}