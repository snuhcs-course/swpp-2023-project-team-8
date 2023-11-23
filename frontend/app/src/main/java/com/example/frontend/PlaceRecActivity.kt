package com.example.frontend

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.frontend.api.AuthAPI
import com.example.frontend.api.PlaceAPI
import com.example.frontend.model.PlaceModel
import com.example.frontend.ui.theme.FrontendTheme
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaceRecActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // averagedLocation 넘겨 받기
        val averagedLocation: LatLng? = intent.getParcelableExtra("averagedLocation")
        val userName: String? = intent.getStringExtra("userName")
        val call = defaultRecAPI().recommend(averagedLocation)

        call.enqueue(object : Callback<List<PlaceModel>> {
            override fun onResponse(call: Call<List<PlaceModel>>, response: Response<List<PlaceModel>>) {
                if (response.isSuccessful) {
                    val placeModels: List<PlaceModel>? = response.body()
                } else {

                }
            }

            override fun onFailure(call: Call<List<PlaceModel>>, t: Throwable) {
            }
        })

        setContent {
            FrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    {
                        // PlaceRecUI
                        PlaceRecUI("Android", modifier = Modifier.align(Alignment.TopCenter))

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(300.dp)
                                .padding(top = 250.dp)
                        ) {
                            MapUI(userName, LatLng(126.9511, 37.4594))
                            //MapUI("Android", userLocationReceived)
                        }

                    }
                }
            }

        }
    }
}

fun defaultRecAPI(): PlaceAPI {
    var url = "http://10.0.2.2:3000"
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(PlaceAPI::class.java)
}

@Composable
fun PlaceRecUI(userName: String?, modifier: Modifier = Modifier) {
    var context = LocalContext.current


    
    Box(
        modifier = Modifier
            .fillMaxSize()

    ){
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = Color(0xFFF3EDF7))

        )
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
                        val nextIntent = Intent(context, MapActivity::class.java)
                        context.startActivity(nextIntent)
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


            Spacer(modifier = Modifier.weight(0.4f))
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = userName?.let { "$userName 님, 이런 장소는 어때요?" } ?: "알 수 없는 사용자",
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),
                letterSpacing = 0.5.sp,
            ),
            modifier = Modifier
                .padding(top = 70.dp)
                .padding(start = 8.dp)
        )

        Spacer(modifier = Modifier
                       .height(200.dp)
                       .width(300.dp))


    }
}

@Preview(showBackground = true)
@Composable
fun PlaceRecUIPreview() {
    FrontendTheme {
        PlaceRecUI("Android")
    }
}