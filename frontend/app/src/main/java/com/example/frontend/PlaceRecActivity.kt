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
import com.example.frontend.ui.theme.FrontendTheme
import com.google.android.gms.maps.model.LatLng

class PlaceRecActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 사용자의 현재 위치 받아오기!
        val userLocationReceived = intent.getParcelableExtra<LatLng>("userLocation")

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
                            MapUI("Android", LatLng(1.35, 103.87))
                            //MapUI("Android", userLocationReceived)
                        }

                    }
                }
            }

        }
    }
}

@Composable
fun PlaceRecUI(name: String, modifier: Modifier = Modifier) {
    var context = LocalContext.current

    // user name 받아오기
    var username by remember { mutableStateOf("") }
    
    
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

        Text( // 닉네임 받아오기
            text = "{username}님, 이런 장소는 어때요?",
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