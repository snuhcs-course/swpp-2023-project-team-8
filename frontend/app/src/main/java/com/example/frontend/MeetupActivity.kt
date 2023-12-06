package com.example.frontend

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import com.example.frontend.ui.component.CustomButton
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.ui.theme.Purple80
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontend.model.UserWithLocationModel
import com.example.frontend.repository.FriendsViewModel
import com.example.frontend.repository.InviteFriendViewModel
import com.example.frontend.usecase.ListFriendUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeetupActivity : ComponentActivity() {
    private val viewModelCheck: InviteFriendViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentLocation = this.intent.getParcelableExtra("currentLocation")?: LatLng(10.1, 23.1)
        setContent {
            FrontendTheme {
                val navController = rememberNavController()
                val selectedFriends = rememberSaveable { mutableStateOf(listOf<Long>()) }
                val viewModel: FriendsViewModel = viewModel()
                val friendsList by viewModel.friendsList.observeAsState(emptyList())
                val userIds = rememberSaveable { mutableStateOf(LongArray(0)) }
                val selectedName = rememberSaveable { mutableStateOf("") }

                LaunchedEffect(Unit) {
                    viewModel.fetchFriends()
                }

                NavHost(navController = navController, startDestination = "meetupUI") {
                    composable("meetupUI") { MeetupUI(navController, selectedFriends) }
                    composable("friendListUI") {
                        FriendListUI(selectedFriends, {},viewModelCheck, friendsList, navController)
                    }
                    composable("placeRecUI"){
                      PlaceRecUI(selectedName.value, userIds.value,currentLocation, Modifier, navController, LocalContext.current)
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetupUI(navController: NavController, selectedFriends: MutableState<List<Long>>) {

    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var context = LocalContext.current
    val activity = LocalContext.current as? ComponentActivity

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Color(0xFFF3EDF7))
        ) {
            Box {
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
                        .padding(top = 16.dp)
                        .width(400.dp)
                        .height(64.dp)
                )
                Button(
                    onClick = {
                        activity?.finish()
                    },

                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                        .padding(start = 16.dp, top = 16.dp),
                    colors = ButtonDefaults.buttonColors(Purple80)
                ) {
                    Text(text = "취소")
                }
            }

        }
        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("제목") },
            modifier = Modifier.width(310.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("설명") },
            modifier = Modifier.width(310.dp)
        )

        val datePickerState =
            rememberDatePickerState(
                initialSelectedDateMillis = System.currentTimeMillis(),
                initialDisplayMode = DisplayMode.Input
            )
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            DatePicker(state = datePickerState, modifier = Modifier.padding(16.dp))
        }

        val timePickerState = rememberTimePickerState()
        TimeInput(timePickerState)

        Spacer(modifier = Modifier.height(30.dp))

        Row {

            Text(
                text = "친구 초대: ${selectedFriends.value.size}명",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),

                    ),

                modifier = Modifier
                    .width(180.dp)
                    .height(64.dp)
                    .padding(start = 40.dp)
            )

            Button(
                onClick = {
                    navController.navigate("friendListUI")
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically) // Center the button vertically inside the Row
                    .offset(y = (-19).dp), colors = ButtonDefaults.buttonColors(Purple80)
            ) {
                Text(text = "초대하기")
            }

        }
        CustomButton(
            buttonText = "장소 선택",
            onClickHandler = {
                navController.navigate("placeRecUI")
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendListUI(
    selectedFriends: MutableState<List<Long>>,
    onSelectionComplete: () -> Unit,
    viewModel: InviteFriendViewModel,
    friendsList: List<UserWithLocationModel>,
    navController: NavController
) {
    var searchQuery by rememberSaveable  { mutableStateOf("") }
    var isSearchClicked by remember { mutableStateOf(false) }
    val selectedFriendIds = remember(selectedFriends.value) { mutableStateOf(selectedFriends.value) }
    val friendUseCase = ListFriendUseCase(LocalContext.current)
    val friendlist = remember { mutableStateOf<List<UserWithLocationModel>>(emptyList()) }
    val checkedStates = remember { mutableStateMapOf<Long, Boolean>() }

    LaunchedEffect(Unit) {
        if (friendlist.value.isEmpty()) {
            friendUseCase.fetch { fetchedFriends ->
                friendlist.value = fetchedFriends
            }
        }
    }
    val filteredFriends = if (isSearchClicked) {
        friendlist.value.filter { it.name.contains(searchQuery, ignoreCase = true) }
    } else {
        friendlist.value
    }

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "친구 초대") },
            actions = {
                IconButton(onClick = {
                    isSearchClicked = !isSearchClicked
                }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            }
        )
        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(50.dp),
            textStyle = TextStyle(fontSize =20.sp)

        )

        LazyColumn {
            items(filteredFriends) { friend ->
                FriendListItem(
                    friendName = friend.name,
                    isSelected = viewModel.checkedStatesFlow.value[friend.id] ?: false,
                    onItemSelected = { isSelected ->
                        viewModel.updateCheckedState(friend.id, isSelected)
                        checkedStates[friend.id] = isSelected
                        if (isSelected) {
                            selectedFriendIds.value = selectedFriendIds.value + friend.id
                        } else {
                            selectedFriendIds.value = selectedFriendIds.value - friend.id
                        }
                    }
                )
            }
        }
        Button(
            onClick = {
                selectedFriends.value = selectedFriendIds.value.distinct()
                onSelectionComplete()
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)

        ) {
            Text("완료")
        }
    }
}

@Composable
fun FriendListItem(friendName: String, isSelected: Boolean, onItemSelected: (Boolean) -> Unit) {
    val rememberedSelectedState = remember { mutableStateOf(isSelected) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                val updatedState = !rememberedSelectedState.value
                rememberedSelectedState.value = updatedState
                onItemSelected(updatedState)
            })
            .padding(16.dp)
    ) {
        Text(friendName, modifier = Modifier.weight(1f))
        Checkbox(
            checked = rememberedSelectedState.value,
            onCheckedChange = {
                rememberedSelectedState.value = it
                onItemSelected(it)
            }
        )
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
//            MeetupUI()
        }
    }
}
