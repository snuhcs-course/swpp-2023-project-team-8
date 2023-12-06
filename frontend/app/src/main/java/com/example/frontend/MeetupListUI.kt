package com.example.frontend

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.frontend.ui.theme.FrontendTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.frontend.data.defaultMeetups
import com.example.frontend.model.MeetUpResponse
import com.example.frontend.model.MeetupModel
import com.example.frontend.ui.component.LoadingIndicator
import com.example.frontend.ui.component.MyTopAppBar
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.usecase.ListMeetUpUseCase
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class MeetupListUI : ComponentActivity() {

    var meetups by mutableStateOf<List<MeetUpResponse>>(emptyList())
    var context = this

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val meetUpUseCase = ListMeetUpUseCase(this)

            LaunchedEffect(Unit) {
                meetUpUseCase.fetch { fetchedMeetUps ->
                    meetups = fetchedMeetUps
                }
            }

            FrontendTheme {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFFF3EDF7))
                )
                MyTopAppBar(title = "약속 목록")
                
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.padding(top = 54.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (meetups.isEmpty()) {
                        LoadingIndicator()
                    } else {
                        MeetUpList(meetups)
                    }
                }
            }
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MeetUpList(meetups: List<MeetUpResponse>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(meetups) { meetup ->
            MeetUpItem(meetup)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MeetUpItem(meetup: MeetUpResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = meetup.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = meetup.meetAt.format(
                    DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.MEDIUM
                    )
                ), style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = meetup.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Participants:", style = MaterialTheme.typography.titleSmall)
            meetup.users.forEach { user ->
                Text(text = "- ${user.name}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ShowMeetUpUIPreview() {
    var context = LocalContext.current
    var meetups = defaultMeetups
    FrontendTheme {
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
                text = "약속 목록",
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF1D1B20)
                )
            )
        }
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.padding(top = 54.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            if (meetups.isEmpty()) {
                LoadingIndicator()
            } else {
                MeetUpList(meetups)
            }
            MeetUpList(meetups)
        }
    }

}

