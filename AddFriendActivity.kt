package com.example.frontend

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

class AddFriendActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BottomAppBar()
                //SearchFriend()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBar() {
    var buttonClicked by remember { mutableStateOf(false) }
    val friends = listOf(
        Friend("친구1"),
        Friend("친구2"),
        Friend("친구3"),
        Friend("친구4"),
        Friend("친구5"),
        Friend("친구6"),
        Friend("친구7"),
        Friend("친구8"),
        Friend("친구9"),
        Friend("친구10"),
        Friend("친구11"),
        Friend("친구12")
    )

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = {
                            // TODO 뒤로가기 버튼 동작
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "뒤로 가기")
                    }

                    Icon(imageVector = Icons.Default.Person, contentDescription = "프로필")
                    Text(text = "My Name", Modifier.offset(5.dp, 0.dp))

                    // 돋보기 눌렀을 때 친구검색 창 띄우기 , Composable 함수를 호출하기 위해 buttonClicked = true
                    IconButton(
                        onClick = {
                            buttonClicked = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Localized description",
                            Modifier.offset(120.dp, 0.dp)
                        )
                    }
                },

                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* TODO +버튼 눌렀을 때 친구추가 작업 */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            )
        },
    )
    { innerPadding ->
        if (buttonClicked) {
            SearchFriend()
        } else {
            Text(
                modifier = Modifier.padding(innerPadding),
                text = FriendList(friends = friends)
            )
        }
    }
}

fun Text(modifier: Modifier, text: Unit) {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFriend(){
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
                // TODO 검색 창을 띄우고 검색 버튼을 눌렀을 때 동작 추가
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

data class Friend(val name: String) {
    val profileImageResource: Int
        get() {
            // FriendList 이름 앞에 프로필 사진 Getter
            TODO()
        }
}

@Composable
fun FriendList(friends: List<Friend>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        friends.forEach { friend ->
            FriendItem(friend)
        }
    }
}

@Composable
fun FriendItem(friend: Friend) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = friend.profileImageResource),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(shape = MaterialTheme.shapes.small)
                .background(Color.Gray)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = friend.name, modifier = Modifier.align(Alignment.CenterVertically))
    }
}