package com.example.frontend

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontend.model.UserWithLocationModel
import com.example.frontend.repository.FriendsViewModel
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
import org.w3c.dom.Text
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
                    FriendsMapUI(currentLocation)
                }
            }
        }
//        val intent = Intent(this, PlaceRecActivity::class.java)
//        intent.putExtra("userLocation", currentLocation)
//        startActivity(intent)

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
                    FriendsMapUI(currentLocation)
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
    modifier: Modifier = Modifier
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

            // Bottom bar at the bottom
            BottomBar(currentLocation)
        }
    }
}

@Composable
fun FriendsMapUI(currentLocation: LatLng?) {
    val viewModel: FriendsViewModel = viewModel()
    val friendsList by viewModel.friendsList.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        while (isActive) {
            viewModel.fetchFriends()
            delay(3000)
        }
    }

    MapUI(currentLocation, friendsList)
}


@Preview(showBackground = true)
@Composable
fun MapUIPreview() {
    FrontendTheme {
        MapUI(LatLng(1.35, 103.87), emptyList())
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
fun BottomBar(currentLocation: LatLng?) {
    var context = LocalContext.current

    val icons = listOf(
        Icons.Default.Star,
        Icons.Outlined.AccountBox,
        Icons.Outlined.DateRange,
        Icons.Outlined.CheckCircle,
        Icons.Outlined.Settings,
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
                    when (icon) {

                        icons[0] -> {
                            // MeetUp 생성으로 이동
                            val nextIntent = Intent(context, MeetupActivity::class.java)
                            nextIntent.putExtra("currentLocation", currentLocation)
                            context.startActivity(nextIntent)
                        }

                        icons[1] -> {
                            // Friend List 이동
                            val nextIntent = Intent(context, AddFriendActivity::class.java)
                            context.startActivity(nextIntent)
                        }

                        icons[2] -> {
                            // 약속 list
                            //val nextIntent = Intent(context, ::class.java)
                            //context.startActivity(nextIntent)
                        }

                        icons[3] -> {
                            // MissionActivity 이동
                            val nextIntent = Intent(context, MissionActivity::class.java)
                            context.startActivity(nextIntent)
                        }
                        icons[4] -> {
                            // userInfo로 이동
                            val nextIntent = Intent(context, UserInfoActivity::class.java)
                            //val nextIntent = Intent(context, PlaceRecActivity::class.java)
                            context.startActivity(nextIntent)

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