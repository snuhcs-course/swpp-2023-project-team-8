package com.example.frontend

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.frontend.data.defaultfriendIdsList
import com.example.frontend.model.PlaceModel
import com.example.frontend.repository.UserContextRepository
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.usecase.ListPlaceUseCase
import com.google.android.gms.maps.model.LatLng

@Composable
fun PlaceRecUI(

    userIds: LongArray,
    currentLocation: LatLng,
    modifier: Modifier = Modifier,
    navController: NavController,
    context: Context
) {
    var userName by remember { mutableStateOf(UserContextRepository(context).getUserName()) }
    var places by remember { mutableStateOf<List<PlaceModel>>(emptyList()) }

    val placeUseCase = remember { ListPlaceUseCase(context, currentLocation, userIds) }
    var selectedPlace by remember { mutableStateOf<PlaceModel?>(null) } // Added state for selected place

    LaunchedEffect(Unit) {
        placeUseCase.fetch { fetchedPlaces ->
            places = fetchedPlaces
        }
    }
    Log.d("place", places.toString())

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = Color(0xFFF3EDF7))
        )
        Column() {
            Row(
                modifier = Modifier
                    .height(54.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier
                        .size(46.dp)
                        .clickable {
                            navController.popBackStack()
                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier,
                    text = "장소 추천",
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF1D1B20)
                    )
                )
            }
            Text(
                text = userName?.let { "$userName 님, 이런 장소는 어때요?" } ?: "알 수 없는 사용자",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(800),
                    color = Color(0xFF000000),
                    letterSpacing = 0.5.sp,
                ),
                modifier = Modifier
                    .padding(start = 8.dp)
            )
            PlaceList(
                placeModels = places,
                onPlaceSelected = {
                    selectedPlace = it
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .height(300.dp)
                    .padding(bottom = 20.dp)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                MapUI(currentLocation, emptyList(), onClick = {})
            }

            // Display the selected place information
            selectedPlace?.let { place ->
                Text(
                    text = "Selected Place: ${place.name}", // Display selected place information
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

    }

}


@Composable
fun PlaceList(placeModels: List<PlaceModel>, onPlaceSelected: (PlaceModel) -> Unit) {
    LazyRow {
        items(placeModels) { place ->
            PlaceItem(
                place = place,
                onPlaceSelected = onPlaceSelected
            )
        }
    }
}

@Composable
fun PlaceItem(place: PlaceModel, onPlaceSelected: (PlaceModel) -> Unit) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(300.dp)
            .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
            .clickable { onPlaceSelected(place) } // Invoke onPlaceSelected when the item is clicked
    ) {
        Text(text = place.name?:"", modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceRecUIPreview() {
    FrontendTheme {
        PlaceRecUI("tittle","2023-12-25", defaultfriendIdsList.toLongArray(), LatLng(10.1,1.2), modifier = Modifier, navController = NavController(LocalContext.current), LocalContext.current)

    }
}