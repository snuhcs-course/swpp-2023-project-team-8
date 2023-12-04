package com.example.frontend

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frontend.data.defaultfriendList
import com.example.frontend.model.UserWithLocationModel
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.usecase.FriendUseCase
import com.example.frontend.usecase.MissionUseCase

class AddFriendActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        var friendlist by mutableStateOf<List<UserWithLocationModel>>(emptyList())

        setContent {
            val friendUseCase = FriendUseCase(this)

            LaunchedEffect(Unit) {
                friendUseCase.fetch { fetchedFriends ->
                    friendlist = fetchedFriends
                }
            }
            var showSearch by remember { mutableStateOf(false) }
            if (friendlist.isEmpty()) {
                LoadingIndicator()
            } else {
                FriendList(friendlist)
            }
            FriendScreen(showSearch = showSearch, onShowSearchChanged = { showSearch = it }, friendList = friendlist)
        }
    }
}


@Composable
fun FriendList(friends: List<UserWithLocationModel>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(friends) { friend ->
                FriendItem(friend)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun FriendItem(friend: UserWithLocationModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = friend.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center)
        )
    }
}
@Composable
fun FriendScreen(showSearch: Boolean, onShowSearchChanged: (Boolean) -> Unit, friendList: List<UserWithLocationModel>) {
    BottomAppBar(onSearchClicked = { onShowSearchChanged(true) }, friendList = friendList)
    if (showSearch) {
        SearchFriend()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBar(onSearchClicked: () -> Unit, friendList:List<UserWithLocationModel>) {
    var buttonClicked by remember { mutableStateOf(false) }
    var context = LocalContext.current
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = {
                            val nextIntent = Intent(context, MapActivity::class.java)
                            context.startActivity(nextIntent)
                            if (context is Activity) {
                                context.finish()
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "뒤로 가기")
                    }

                    Icon(imageVector = Icons.Default.Person, contentDescription = "프로필")
                    Text(text = "My Name", Modifier.offset(5.dp, 0.dp))


                    // 돋보기 눌렀을 때 친구검색 창 띄우기 , Composable 함수를 호출하기 위해 buttonClicked = true
                    IconButton(
                        onClick = {
                            onSearchClicked()
                        },
                        Modifier.offset(140.dp, 0.dp)
                    ) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Localized description"
                        )
                    }
                },

                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /*  +버튼 눌렀을 때 친구추가 작업 */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            )
        },
    ) { innerPadding ->
        Text(
            modifier = Modifier.padding(innerPadding),
            text = "친구목록"

        )
        FriendList(friendList)

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFriend() {
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        label = { Text("Search") },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                // 검색 창을 띄우고 검색 버튼을 눌렀을 때 동작 추가
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ShowAddFriendUIPreview() {
    var showSearch by remember { mutableStateOf(false) }
    FrontendTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FriendScreen(showSearch = showSearch, onShowSearchChanged = { showSearch = it }, friendList = defaultfriendList)
        }
    }
}
