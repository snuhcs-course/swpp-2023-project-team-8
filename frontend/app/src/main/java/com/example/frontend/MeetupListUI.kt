package com.example.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.frontend.ui.theme.FrontendTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.frontend.api.MeetUpService
import com.example.frontend.model.MeetupModel
import com.example.frontend.model.MissionModel
import com.example.frontend.ui.login.getAuthtoken
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MeetupListUI : ComponentActivity() {
    val authToken: String by lazy { getAuthtoken(this) }
    val meetupService: MeetUpService by lazy { defaultMeetupService(authToken) }
    var meetups by mutableStateOf<List<MeetupModel>>(emptyList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            fetchMeetUps()

            FrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (meetups.isEmpty()) {
                        LoadingIndicator()
                    } else {
                        PromiseList(meetups)
                    }
                    PromiseList(meetups)
                }
            }
        }
    }
    private fun fetchMeetUps() {
        val call = meetupService.getMeetUps()

        call.enqueue(object : Callback<List<MeetupModel>> {
            override fun onResponse(
                call: Call<List<MeetupModel>>,
                response: Response<List<MeetupModel>>
            ) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        meetups = response.body() ?: emptyList()
                    }
                } else {

                }
            }
            override fun onFailure(call: Call<List<MeetupModel>>, t: Throwable) {
                meetups = defaultMeetups
            }
        })
    }
}

data class Promise(
    val friends: List<String>,
    val time: String,
    val date: String,
    val location: String
)
fun defaultMeetupService(authToken: String): MeetUpService {
    val retrofit = createAuthenticatedRetrofit(authToken)
    return retrofit.create(MeetUpService::class.java)
}

val defaultMeetups = listOf(
    MeetupModel("카페", listOf("친구1", "친구2", "친구3"), "14:00", "2023-12-01", LatLng(13.1, 23.1))
)
object FakeServer {
    fun getPromises(): List<MeetupModel> {
        return defaultMeetups
    }
}


@Composable
fun PromiseList(meetups: List<MeetupModel>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(meetups) { meetup ->
            PromiseItem(meetup)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PromiseItem(promise: MeetupModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
                Text(promise.friends.joinToString(", "))
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                Text("${promise.date} ${promise.time}")
            }
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(imageVector = Icons.Default.Place, contentDescription = null)
            //Text(promise.location)
        }
    }
}


