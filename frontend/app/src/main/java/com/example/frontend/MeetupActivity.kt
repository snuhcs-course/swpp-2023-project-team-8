package com.example.frontend


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.ui.theme.Purple80
import com.google.android.gms.maps.model.LatLng

class MeetupActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the currentLocation from the intent

        val currentLocation: LatLng? = intent.getParcelableExtra("currentLocation")
        setContent {
            FrontendTheme {
              //  MeetupUI(LatLng(1.35, 103.87)) {
                MeetupUI(currentLocation) {
                    // Handle switch to register
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetupUI(currentLocation: LatLng?, onSwitchToRegister: () -> Unit) {

    var title by remember { mutableStateOf("") }
    var hour by remember { mutableStateOf("") }
    var minute by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var explain by remember { mutableStateOf("") }
    var context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Color(0xFFF3EDF7))
        ) {
            Column{
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "밋업 생성",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .width(400.dp)
                        .height(64.dp)
                )

            }

        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("제목") },

        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(//Picker로 교체
            value = date,
            onValueChange = { date = it },
            label = { Text("날짜") },
            )

        Spacer(modifier = Modifier.height(16.dp))
        Row{
            OutlinedTextField(//Picker로 교체
                value = hour,
                onValueChange = { hour = it },
                label = { Text("시") },
                modifier = Modifier
                    .offset(x = (-15).dp)
                    .background(Color.Transparent)
                    .width(96.dp)
                    .height(72.dp)
            )
            Text(
                text = ":",
                style = TextStyle(
                    fontSize = 57.sp,
                    lineHeight = 64.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF1D1B20),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .offset(x = (0).dp)
            )
            OutlinedTextField(//Picker로 교체
                value = minute,
                onValueChange = { minute = it },
                label = { Text("분") },
                modifier = Modifier
                    .offset(x = (15).dp)
                    .background(Color.Transparent)
                    .width(96.dp)
                    .height(72.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = explain,
            onValueChange = { explain = it },
            label = { Text("설명") },
        )
        Spacer(modifier = Modifier.height(35.dp))

        Row{

                Text(
                    text = "친구 초대 - ",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),

                        ),

                    modifier = Modifier
                        .width(130.dp)
                        .height(64.dp)
                        .padding(start = 40.dp)
                )



                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically) // Center the button vertically inside the Row
                        .offset(y = (-19).dp)
                    , colors = ButtonDefaults.buttonColors(Purple80)
                ) {
                    Text(text = "초대하기")
                }


        }

        Spacer(modifier = Modifier.height(140.dp))
        Row{
            Button(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.CenterVertically) // Center the button vertically inside the Row
                
                , colors = ButtonDefaults.buttonColors(Purple80)
            ) {
                Text(text = "공개")
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.CenterVertically) // Center the button vertically inside the Row

                , colors = ButtonDefaults.buttonColors(Purple80)
            ) {
                Text(text = "비공개")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))


        // (현재 유저 위치) currentLocation
        // averagedLocation: 친구들 위치와 현재 유저 위치의 평균 값
        ///////////////////////////////////////////////////////////////////
        //
        val averagedLocation: LatLng? = LatLng(1.35, 103.87)
        ///////////////////////////////////////////////////////////////////
        CustomButton(
            buttonText = "장소 선택",
            onClickHandler = {
                val nextIntent = Intent(context, PlaceRecActivity::class.java)
                nextIntent.putExtra("averagedLocation", averagedLocation)
                context.startActivity(nextIntent)

            })
    }
}




@Preview(showBackground = true)
@Composable
fun MeetupUIPreview() {
    FrontendTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MeetupUI(LatLng(1.35, 103.87)) {

            }
        }
    }
}
