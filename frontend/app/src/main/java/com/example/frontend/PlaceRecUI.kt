package com.example.frontend

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import com.example.frontend.data.defaultPlaces
import com.example.frontend.data.defaultfriendIdsList
import com.example.frontend.model.PlaceModel
import com.example.frontend.model.PlaceResponse
import com.example.frontend.repository.UserContextRepository
import com.example.frontend.ui.component.LoadingIndicator
import com.example.frontend.ui.component.MapWithMarker
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.usecase.ListPlaceUseCase
import com.google.android.gms.maps.model.LatLng

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlaceRecUI(
    meetUpPlace: MutableState<Long>,
    selectedName: MutableState<String>,
    userIds: List<Long>,
    currentLocation: LatLng,
    modifier: Modifier = Modifier,
    navController: NavController,
    context: Context
) {
    var userName = UserContextRepository.ofContext(context).getUserName()
    var places by remember { mutableStateOf<List<PlaceResponse>>(emptyList()) }
    val placeUseCase =  ListPlaceUseCase(context, userIds)
    var selectedPlace by remember { mutableStateOf<PlaceResponse?>(null) }
    var selectedPlaceName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        placeUseCase.fetch { fetchedPlaces ->
            places = fetchedPlaces
        }
    }
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
            if (places.isEmpty()) {
                LoadingIndicator()
            } else {
                PlaceList(
                    placeModels = places,
                    onPlaceSelected = { selectedPlace = it },
                    selectedPlace = selectedPlace,
                    onSelectionChange = { selectedPlace = it }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .height(200.dp)
                    .padding(bottom = 20.dp)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                MapWithMarker(currentLocation, emptyList())
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                selectedPlace?.let { place ->
                    Text(
                        text = "선택한 장소: ${place.name}",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                    selectedPlaceName = place.name
                    meetUpPlace.value = place.id
                }
                if (selectedPlace == null) {
                    Text(
                        text = "선택한 장소:",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Button(
                    onClick = {
                        selectedName.value = selectedPlaceName?:""
                        navController.popBackStack()
                    },
                    modifier = Modifier
                    //  .align(Alignment.End)

                ) {
                    Text("완료")
                }
            }
        }

    }

}
@Composable
fun PlaceList(
    placeModels: List<PlaceResponse>,
    onPlaceSelected: (PlaceResponse) -> Unit,
    selectedPlace: PlaceResponse?,
    onSelectionChange: (PlaceResponse?) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(placeModels) { place ->
            PlaceItem(
                place = place,
                onPlaceSelected = onPlaceSelected,
                isSelected = selectedPlace == place,
                onSelectionChange = onSelectionChange
            )
        }
    }
}

@Composable
fun PlaceItem(
    place: PlaceResponse,
    onPlaceSelected: (PlaceResponse) -> Unit,
    isSelected: Boolean,
    onSelectionChange: (PlaceResponse?) -> Unit
) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .background(
                color = if (isSelected) Color.DarkGray else Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(top = 30.dp, start = 10.dp)
            .clickable {
                if (isSelected) {
                    onSelectionChange(null)
                } else {
                    onSelectionChange(place)
                    onPlaceSelected(place)
                }
            }
    ) {
        Text(text = place.name ?: "", modifier = Modifier)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PlaceRecUIPreview() {
    FrontendTheme {
        PlaceRecUI(mutableStateOf(0),mutableStateOf("A"),defaultfriendIdsList, LatLng(10.1,1.2), modifier = Modifier, navController = NavController(LocalContext.current), LocalContext.current)
    }
}