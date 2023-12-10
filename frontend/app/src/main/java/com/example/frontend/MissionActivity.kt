package com.example.frontend


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.frontend.data.defaultMissions
import com.example.frontend.model.MissionModel
import com.example.frontend.ui.component.MyTopAppBar
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.ui.theme.Purple40
import com.example.frontend.usecase.ListMissionUseCase

class MissionActivity : ComponentActivity() {
    var missions by mutableStateOf<List<MissionModel>>(emptyList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val missionUseCase = ListMissionUseCase(this)

            LaunchedEffect(Unit) {
                missionUseCase.fetch { fetchedMissions ->
                    missions = fetchedMissions
                }
            }
            FrontendTheme {
                ShowMissionUI(missions = missions) {
                    // Handle switch to register
                }
            }
        }
    }
}

@Composable
fun ShowMissionUI(missions: List<MissionModel>, onSwitchToRegister: () -> Unit) {
    val indexedMissions = missions.withIndex().toList()
    var title by remember { mutableStateOf("") }
    var showMoreDescriptions by remember { mutableStateOf(mutableMapOf<Int, Boolean>()) }
    var context = LocalContext.current
    Column {
        // Header with Back button and title
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFF3EDF7))
        )
        MyTopAppBar(title = "달성 목록")

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
                color = MaterialTheme.colorScheme.background
            ) {
                GridItems(items = indexedMissions) { indexedMission, itemModifier ->
                    val (index, mission) = indexedMission
                    Box(
                        modifier = Modifier
                            .then(itemModifier)
                            .height(195.dp)
                            .padding(top = 24.dp)
                            .background(mission.color, shape = MaterialTheme.shapes.medium)
                    ) {

                        Column {
                            Spacer(modifier = Modifier.height(144.dp))
                            Row {
                                Spacer(modifier = Modifier.width(75.dp))
                                Box(
                                    modifier = Modifier
                                        .clickable {
                                            title = "미션 ${index + 1}"
                                            showMoreDescriptions =
                                                showMoreDescriptions.toMutableMap().apply {
                                                    this[index] = true
                                                }
                                        }
                                ) {
                                    if (mission.userCompleted == true) {
                                        Icon(
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = "Completed",
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .offset(x = 7.dp, y = (-134).dp),
                                            tint = Purple40
                                        )
                                    }
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
                                text = "미션 ${index + 1}",
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
                                    text = mission.name,
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
                        if (showMoreDescriptions[index] == true) {
                            ShowMoreDescriptionDialog(
                                title = mission.name,
                                description = "",
                                onDismissRequest = {
                                    showMoreDescriptions =
                                        showMoreDescriptions.toMutableMap().apply {
                                            remove(index)
                                        }
                                },
                                showMore = if (mission.userCompleted == null || mission.userCompleted == false) "${mission.description} 현재: ${mission.userProgress ?: 0}회" else mission.description
                            )
                        }
                    }
                }
            }
        }
    }
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
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium
                )
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
fun GridItems(
    items: List<IndexedValue<MissionModel>>,
    itemContent: @Composable (IndexedValue<MissionModel>, Modifier) -> Unit
) {
    val rows = items.chunked(2)

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val spacing = 16.dp
    val padding = 24.dp
    val itemWidth = (screenWidth - padding * 2 - spacing) / 2

    Column {
        for (rowItems in rows) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing),
                modifier = Modifier.padding(horizontal = padding)
            ) {
                for (mission in rowItems) {
                    itemContent(mission, Modifier.width(itemWidth))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
