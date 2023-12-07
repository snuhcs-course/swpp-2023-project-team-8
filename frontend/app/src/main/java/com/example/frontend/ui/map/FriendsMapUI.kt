package com.example.frontend.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontend.ui.component.BottomBar
import com.example.frontend.ui.component.MapWithMarker
import com.example.frontend.utilities.SNU_POLYGON
import com.example.frontend.viewmodel.FriendsViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

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
            BottomBar()
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            MapWithMarker(currentLocation, friendsList, SNU_POLYGON)
            FloatingActionButton(
                onClick = { onClick() },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    }
}
