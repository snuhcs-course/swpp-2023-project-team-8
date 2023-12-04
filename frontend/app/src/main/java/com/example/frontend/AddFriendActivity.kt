package com.example.frontend

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

class AddFriendActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showSearch by remember { mutableStateOf(false) }
            FriendScreen(showSearch = showSearch, onShowSearchChanged = { showSearch = it })
        }
    }
}

@Composable
fun FriendScreen(showSearch: Boolean, onShowSearchChanged: (Boolean) -> Unit) {
    BottomAppBar(onSearchClicked = { onShowSearchChanged(true) })
    if (showSearch) {
        SearchFriend()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBar(onSearchClicked: () -> Unit) {
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