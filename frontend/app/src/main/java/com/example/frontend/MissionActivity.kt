package com.example.frontend


import android.app.Activity
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.frontend.api.MissionAPI
import com.example.frontend.model.MissionModel
import com.example.frontend.repository.UserContextRepository
import com.example.frontend.ui.theme.FrontendTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissionActivity : ComponentActivity() {
    val userContextRepository = UserContextRepository(this)
    val authToken: String = userContextRepository.getAuthToken()
    val missionApi: MissionAPI by lazy { defaultMissionAPI(authToken) }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //val authToken = getAuthtoken(this)
            val missionApi = defaultMissionAPI(authToken)
            //var missions by remember { mutableStateOf(emptyList<MissionModel>()) }
            fetchMissions()

            FrontendTheme {
                ShowMissionUI(missions = missions) {
                    // Handle switch to register
                }
            }
        }
    }

    var missions by mutableStateOf<List<MissionModel>>(emptyList())
    private fun fetchMissions() {
        val call = missionApi.getMissionList()

        call.enqueue(object : Callback<List<MissionModel>> {
            override fun onResponse(
                call: Call<List<MissionModel>>,
                response: Response<List<MissionModel>>
            ) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        missions = response.body() ?: emptyList()
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<List<MissionModel>>, t: Throwable) {
                runOnUiThread {
                    missions = defaultMissions
                }
            }
        })
    }
}


fun defaultMissionAPI(authToken: String): MissionAPI {
    val retrofit = createAuthenticatedRetrofit(authToken)
    return retrofit.create(MissionAPI::class.java)
}

@Composable
fun ShowMissionUI(missions: List<MissionModel>, onSwitchToRegister: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var showMoreDescriptions by remember { mutableStateOf(mutableMapOf<String, Boolean>()) }
    var context = LocalContext.current
    Column {
        // Header with Back button and title
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
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
                        // finish current activity
                        if (context is Activity) {
                            context.finish()
                        }
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = Modifier,
                text = "달성 목록",
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF1D1B20)
                )
            )
        }

        if (missions.isEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 200.dp)
                    .size(36.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFFDFD5EC)
            ) {
                GridItems(items = missions) { mission ->
                    Box(
                        modifier = Modifier
                            .padding(top = 24.dp, start = 23.dp)
                            .size(160.dp, 170.dp)
                            .background(mission.color, shape = MaterialTheme.shapes.medium)
                    ) {

                        Column {
                            Spacer(modifier = Modifier.height(144.dp))
                            Row {
                                Spacer(modifier = Modifier.width(75.dp))
                                Box(
                                    modifier = Modifier
                                        .clickable {
                                            title = mission.title
                                            showMoreDescriptions =
                                                showMoreDescriptions.toMutableMap().apply {
                                                    this[mission.title] = true
                                                }
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
                                text = mission.title,
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
                                    text = mission.description,
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
                        if (showMoreDescriptions[mission.title] == true) {
                            ShowMoreDescriptionDialog(
                                title = mission.title,
                                description = getMoreDescription(mission.title, missions),
                                onDismissRequest = {
                                    showMoreDescriptions =
                                        showMoreDescriptions.toMutableMap().apply {
                                            remove(mission.title)
                                        }
                                },
                                showMore = mission.showMore // Pass the showMore value here
                            )
                        }
                    }
                }
            }
        }
    }
}

fun getMoreDescription(missionTitle: String, missions: List<MissionModel>): String {
    // Find the MissionModel for the given missionTitle
    val mission = missions.find { it.title == missionTitle }

    // Provide different descriptions based on the mission title
    return mission?.showMore ?: "$missionTitle 상세 설명 업데이트 필요"
}


@Composable
fun ShowMoreDescriptionDialog(
    title: String,
    description: String,
    showMore: String,
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
                    text = showMore,
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
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { onDismissRequest.invoke() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "닫기")
                }

            }
        }
    }
}

val defaultMissions = listOf(
    MissionModel("미션1", "친구와 우연히 만나기", false, "예상치 못한 장소에서 친구와 마주쳐 보세요!"),
    MissionModel("미션2", "친구와 약속 잡기", false, "친구와 약속을 잡아 보세요!"),
    MissionModel("미션3", "친구와 약속 장소 정하기", false, "3명 이상의 친구와 약속을 잡아 보세요!"),
    MissionModel("미션4", "자하연 근처에서 친구 마주치기", false, "자하연에서 친구와 마주쳐 보세요!"),
    MissionModel("미션5", "관악산 등산하기", false, "관악산에 올라가 보세요!"),
    MissionModel("미션6", "도서관에 한 시간 머물기", false, "도서관에 머물며 책을 읽는 시간을 가져 보세요!"),
    MissionModel("미션7", "친구 세 명과 만나기", false, "친구 세 명과 약속을 잡아 보세요!"),
    MissionModel("미션8", "친구 스무 명 추가하기", false, "친구 20 명을 추가해 보세요!")
)

@Preview(showBackground = true)
@Composable
fun ShowMissionUIPreview() {

    FrontendTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ShowMissionUI(defaultMissions) {

            }
        }
    }
}

@Composable
fun GridItems(items: List<MissionModel>, itemContent: @Composable (MissionModel) -> Unit) {
    val rows = items.chunked(2)

    Column {
        for (rowItems in rows) {
            Row {
                for (mission in rowItems) {
                    itemContent(mission)
                    Spacer(modifier = Modifier.width(16.dp)) // Adjust spacing as needed
                }
            }
            Spacer(modifier = Modifier.height(16.dp)) // Adjust spacing as needed
        }
    }
}