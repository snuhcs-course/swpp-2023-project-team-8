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


class MeetupListUI : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MeetupListUI()
                }
            }
        }
    }
}

data class Promise(
    val friends: List<String>,
    val time: String,
    val date: String,
    val location: String
)

object FakeServer {
    fun getPromises(): List<Promise> {
        return listOf(
            Promise(
                friends = listOf("친구1", "친구2", "친구3"),
                time = "14:00",
                date = "2023-12-12",
                location = "카페"
            ),
            Promise(
                friends = listOf("친구4", "친구5"),
                time = "18:30",
                date = "2023-12-13",
                location = "음식점"
            )

        )
    }
}


@Composable
fun PromiseList() {
    val promises = FakeServer.getPromises()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(promises) { promise ->
            PromiseItem(promise)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PromiseItem(promise: Promise) {
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
            Text(promise.location)
        }
    }
}


