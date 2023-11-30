package com.example.frontend


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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.frontend.api.MissionAPI
import com.example.frontend.api.PlaceAPI
import com.example.frontend.ui.theme.FrontendTheme
import com.google.android.gms.maps.model.LatLng

class MissionActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         setContent {
            FrontendTheme {
                ShowMissionUI() {
                    // Handle switch to register
                }
            }
        }
    }
}

fun defaultMissionAPI(authToken: String): MissionAPI {
    val retrofit = createAuthenticatedRetrofit(authToken)
    return retrofit.create(MissionAPI::class.java)
}

@Composable
fun ShowMissionUI(onSwitchToRegister: () -> Unit) {
    var title by remember { mutableStateOf("") }
    val showMoreDescriptions = remember { mutableStateOf(items.associate { it.first to false }) }

    val items = listOf(
        "미션1" to "친구와 우연히 만나기",
        "미션2" to "친구와 약속 잡기",
        "미션3" to "친구와 약속 장소 정하기",
        "미션4" to "자하연 근처에서 친구 마주치기",
        "미션5" to "관악산 등산하기",
        "미션6" to "도서관에 한 시간 머물기",
        "미션7" to "친구 세 명과 만나기",
        "미션8" to "친구 스무 명 추가하기"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFDFD5EC)
    ) {
        GridItems(items = items) { (missionTitle, missionDescription) ->
            Box(
                modifier = Modifier
                    .padding(top = 24.dp, start = 23.dp)
                    .size(160.dp, 170.dp)
                    .background(Color(0xFFF3EDF7), shape = MaterialTheme.shapes.medium)
            ) {
                Column {
                    Spacer(modifier = Modifier.height(144.dp))
                    Row {
                        Spacer(modifier = Modifier.width(75.dp))
                        Box(
                            modifier = Modifier
                                .clickable {
                                    title = missionTitle
                                    showMoreDescription = true
                                }
                        ) {
                            Text(
                                text = "See More ›",
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

                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = missionTitle,
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
                    Row {
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            text = missionDescription,
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
                if (showMoreDescription) {
                    ShowMoreDescriptionDialog(
                        title = missionTitle,
                        description = getMoreDescription(missionTitle),
                        onDismissRequest = { showMoreDescriptions[missionTitle] = false }
                    )
                }

            }
        }
    }
}

@Composable
fun getMoreDescription(missionTitle: String): String {
    // Provide different descriptions based on the mission title
    return when (missionTitle) {
        "미션1" -> "예상치 못한 장소에서 친구와 마주쳐 보세요!"
        "미션2" -> "친구와 약속을 잡아 보세요!"
        "미션3" -> "3명 이상의 친구와 약속을 잡아 보세요!"
        "미션4" -> "자하연에서 친구와 마주쳐 보세요!"
        "미션5" -> "관악산에 올라가 보세요!"
        "미션6" -> "도서관에 머물며 책을 읽는 시간을 가져 보세요!"
        "미션7" -> "친구 세 명과 약속을 잡아 보세요!"
        "미션8" -> "친구 20 명을 추가해 보세요!"

        else -> "$missionTitle 상세 설명"
    }
}


@Composable
fun ShowMoreDescriptionDialog(
    title: String,
    description: String,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest.invoke()
        }
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background, shape = MaterialTheme.shapes.medium)
        ) {
            Column {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF6750A4),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.15.sp,
                    ),
                    modifier = Modifier
                        .width(250.dp)
                        .height(30.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = description,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF1D1B20),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.5.sp,
                    ),
                    modifier = Modifier
                        .width(250.dp)
                        .height(60.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShowMissionUIPreview() {
    FrontendTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ShowMissionUI {

            }
        }
    }
}
@Composable
fun GridItems(items: List<Pair<String, String>>, itemContent: @Composable (Pair<String, String>) -> Unit) {
    LazyColumn {
        items(items.windowed(2, 2, partialWindows = true)) { rowItems ->
            Row {
                for ((title, description) in rowItems) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        itemContent(title to description)
                    }
                }
            }
        }
    }
}