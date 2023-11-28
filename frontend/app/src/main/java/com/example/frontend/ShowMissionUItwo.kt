package com.example.frontend


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.ui.theme.Purple80


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowMissionUItwo(onSwitchToRegister: () -> Unit) {

    var title by remember { mutableStateOf("") }
    var hour by remember { mutableStateOf("") }
    var minute by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var explain by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFDFD5EC)
    ) {

        Column{

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Color(0xFFF3EDF7))
            ) {
                Row {
                    Spacer(modifier = Modifier.width(5.dp))
                    Column {
                        Spacer(modifier = Modifier.height(11.dp))
                        val icon = Icons.Default.KeyboardArrowLeft
                        IconToggleButton(icon = icon) {

                        }

                    }
                }



                Column {
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "달성 기록",
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


            Row{
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp, start = 23.dp)
                        .size(160.dp, 170.dp)
                        .background(Color(0xFFF3EDF7), shape = MaterialTheme.shapes.medium)

                ) {
                    Column{
                        Spacer(modifier = Modifier.height(144.dp))
                        Row{
                            Spacer(modifier = Modifier.width(75.dp))
                            Box(
                                modifier = Modifier
                                    .clickable{ }

                            ){
                                Text(
                                    text = "See More ›",
// M3/body/medium
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFA6A6A6),
                                        letterSpacing = 0.25.sp,
                                    )
                                )

                            }

                        }
                    }

                    Column{
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "미션1",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF6750A4),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.15.sp,
                            ),
                            modifier = Modifier
                                .width(80.dp)
                                .height(31.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(0.dp)
                                .width(160.dp)
                                .height(1.dp)
                                .background(color = Color(0xFFA6A6A6))

                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row{
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = "친구와 우연히\n만나기",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF1D1B20),

                                    letterSpacing = 0.5.sp,
                                )
                            )
                        }



                    }

                }
                Spacer(modifier = Modifier.width(28.dp))
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(160.dp, 170.dp)
                        .background(Color(0xFFF3EDF7), shape = MaterialTheme.shapes.medium)

                ) {
                    Column{
                        Spacer(modifier = Modifier.height(144.dp))
                        Row{
                            Spacer(modifier = Modifier.width(75.dp))
                            Box(
                                modifier = Modifier
                                    .clickable{ }

                            ){
                                Text(
                                    text = "See More ›",
// M3/body/medium
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFA6A6A6),
                                        letterSpacing = 0.25.sp,
                                    )
                                )

                            }
                        }
                    }

                    Column{
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "미션2",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF6750A4),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.15.sp,
                            ),
                            modifier = Modifier
                                .width(80.dp)
                                .height(31.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(0.dp)
                                .width(160.dp)
                                .height(1.dp)
                                .background(color = Color(0xFFA6A6A6))

                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row{
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = "친구와 약속 잡기",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF1D1B20),

                                    letterSpacing = 0.5.sp,
                                )
                            )
                        }



                    }


                }


            }

            Row{
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp, start = 23.dp)
                        .size(160.dp, 170.dp)
                        .background(Color(0xFFF3EDF7), shape = MaterialTheme.shapes.medium)

                ) {
                    Column{
                        Spacer(modifier = Modifier.height(144.dp))
                        Row{
                            Spacer(modifier = Modifier.width(75.dp))
                            Box(
                                modifier = Modifier
                                    .clickable{ }

                            ){
                                Text(
                                    text = "See More ›",
// M3/body/medium
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFA6A6A6),
                                        letterSpacing = 0.25.sp,
                                    )
                                )

                            }
                        }
                    }

                    Column{
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "미션3",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF6750A4),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.15.sp,
                            ),
                            modifier = Modifier
                                .width(80.dp)
                                .height(31.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(0.dp)
                                .width(160.dp)
                                .height(1.dp)
                                .background(color = Color(0xFFA6A6A6))

                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row{
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = "친구와 약속 장소\n정하기",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF1D1B20),

                                    letterSpacing = 0.5.sp,
                                )
                            )
                        }



                    }



                }
                Spacer(modifier = Modifier.width(28.dp))
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(160.dp, 170.dp)
                        .background(Color(0xFFF3EDF7), shape = MaterialTheme.shapes.medium)

                ) {
                    Column{
                        Spacer(modifier = Modifier.height(144.dp))
                        Row{
                            Spacer(modifier = Modifier.width(75.dp))
                            Box(
                                modifier = Modifier
                                    .clickable{ }

                            ){
                                Text(
                                    text = "See More ›",
// M3/body/medium
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFA6A6A6),
                                        letterSpacing = 0.25.sp,
                                    )
                                )

                            }
                        }
                    }

                    Column{
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "미션4",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF6750A4),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.15.sp,
                            ),
                            modifier = Modifier
                                .width(80.dp)
                                .height(31.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(0.dp)
                                .width(160.dp)
                                .height(1.dp)
                                .background(color = Color(0xFFA6A6A6))

                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row{
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = "자하연 근처에서\n친구 마주치기",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF1D1B20),

                                    letterSpacing = 0.5.sp,
                                )
                            )
                        }



                    }


                }


            }
            Row{
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp, start = 23.dp)
                        .size(160.dp, 170.dp)
                        .background(Color(0xFFF3EDF7), shape = MaterialTheme.shapes.medium)

                ) {
                    Column{
                        Spacer(modifier = Modifier.height(144.dp))
                        Row{
                            Spacer(modifier = Modifier.width(75.dp))
                            Box(
                                modifier = Modifier
                                    .clickable{ }

                            ){
                                Text(
                                    text = "See More ›",
// M3/body/medium
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFA6A6A6),
                                        letterSpacing = 0.25.sp,
                                    )
                                )

                            }
                        }
                    }

                    Column{
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "미션5",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF6750A4),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.15.sp,
                            ),
                            modifier = Modifier
                                .width(80.dp)
                                .height(31.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(0.dp)
                                .width(160.dp)
                                .height(1.dp)
                                .background(color = Color(0xFFA6A6A6))

                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row{
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = "관악산 등산하기",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF1D1B20),

                                    letterSpacing = 0.5.sp,
                                )
                            )
                        }



                    }



                }
                Spacer(modifier = Modifier.width(28.dp))
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(160.dp, 170.dp)
                        .background(Color(0xFFF3EDF7), shape = MaterialTheme.shapes.medium)

                ) {
                    Column{
                        Spacer(modifier = Modifier.height(144.dp))
                        Row{
                            Spacer(modifier = Modifier.width(75.dp))
                            Box(
                                modifier = Modifier
                                    .clickable{ }

                            ){
                                Text(
                                    text = "See More ›",
// M3/body/medium
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFA6A6A6),
                                        letterSpacing = 0.25.sp,
                                    )
                                )

                            }
                        }
                    }

                    Column{
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "미션6",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF6750A4),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.15.sp,
                            ),
                            modifier = Modifier
                                .width(80.dp)
                                .height(31.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(0.dp)
                                .width(160.dp)
                                .height(1.dp)
                                .background(color = Color(0xFFA6A6A6))

                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row{
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = "도서관에 한 시간\n머물기",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF1D1B20),

                                    letterSpacing = 0.5.sp,
                                )
                            )
                        }



                    }


                }


            }
            Row{
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp, start = 23.dp)
                        .size(160.dp, 170.dp)
                        .background(Color(0xFFF3EDF7), shape = MaterialTheme.shapes.medium)

                ) {
                    Column{
                        Spacer(modifier = Modifier.height(144.dp))
                        Row{
                            Spacer(modifier = Modifier.width(75.dp))
                            Box(
                                modifier = Modifier
                                    .clickable{ }

                            ){
                                Text(
                                    text = "See More ›",
// M3/body/medium
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFA6A6A6),
                                        letterSpacing = 0.25.sp,
                                    )
                                )

                            }
                        }
                    }

                    Column{
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "미션7",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF6750A4),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.15.sp,
                            ),
                            modifier = Modifier
                                .width(80.dp)
                                .height(31.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(0.dp)
                                .width(160.dp)
                                .height(1.dp)
                                .background(color = Color(0xFFA6A6A6))

                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row{
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = "친구 세 명과\n만나기",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF1D1B20),

                                    letterSpacing = 0.5.sp,
                                )
                            )
                        }



                    }



                }
                Spacer(modifier = Modifier.width(28.dp))
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(160.dp, 170.dp)
                        .background(Color(0xFFF3EDF7), shape = MaterialTheme.shapes.medium)

                ) {
                    Column{
                        Spacer(modifier = Modifier.height(144.dp))
                        Row{
                            Spacer(modifier = Modifier.width(75.dp))
                            Box(
                                modifier = Modifier
                                    .clickable{ }

                            ){
                                Text(
                                    text = "See More ›",
// M3/body/medium
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFA6A6A6),
                                        letterSpacing = 0.25.sp,
                                    )
                                )

                            }
                        }
                    }

                    Column{
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "미션8",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF6750A4),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.15.sp,
                            ),
                            modifier = Modifier
                                .width(80.dp)
                                .height(31.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(0.dp)
                                .width(160.dp)
                                .height(1.dp)
                                .background(color = Color(0xFFA6A6A6))

                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row{
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(
                                text = "친구 스무 명\n추가하기",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF1D1B20),

                                    letterSpacing = 0.5.sp,
                                )
                            )
                        }



                    }


                }


            }


        }




    }
    Column{
        Spacer(modifier = Modifier.height(64.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray.copy(alpha = 0.5f)) // 투명도를 원하는 값으로 조정합니다.
        ){
            Column{
                Spacer(modifier = Modifier.height(200.dp))
                Row{
                    Spacer(modifier = Modifier.width(20.dp))
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp, start = 23.dp)
                        .size(312.dp, 240.dp)
                        .background(Color(0xFFF3EDF7), shape = MaterialTheme.shapes.medium)


                ) {
                    Column {
                        Spacer(modifier = Modifier.height(200.dp))
                        Row {
                            Spacer(modifier = Modifier.width(240.dp))
                            Box(
                                modifier = Modifier
                                    .clickable { }

                            ) {
                                Text(
                                    text = "Close",
// M3/body/medium
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF6750A4),
                                        letterSpacing = 0.25.sp,
                                    )
                                )

                            }

                        }
                    }

                    Column {
                        Spacer(modifier = Modifier.height(30.dp))

                        Row {
                            Spacer(modifier = Modifier.width(28.dp))
                            Column{
                                Text(
                                    text = "친구와 우연히 만나기",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        lineHeight = 24.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF1D1B20),

                                        letterSpacing = 0.5.sp,
                                    )
                                )

                                Spacer(modifier = Modifier.height(30.dp))

                                Text(
                                    text = "예상치 못한 장소에서 친구를\n만나 보세요!",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        lineHeight = 30.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFA6A6A6),

                                        letterSpacing = 0.5.sp,
                                    )
                                )


                            }

                        }




                    }
                }
                }
            }



        }
    }

}




@Preview(showBackground = true)
@Composable
fun ShowMissionUItwoPreview() {
    FrontendTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ShowMissionUItwo {

            }
        }
    }
}
