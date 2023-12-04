package com.example.frontend

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import com.example.frontend.api.FriendService
import com.example.frontend.api.MissionAPI
import com.example.frontend.model.MissionModel
import com.example.frontend.model.UserWithLocationModel
import com.example.frontend.ui.login.getAuthtoken
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFriendActivity : ComponentActivity() {
    val authToken: String by lazy { getAuthtoken(this) }
    val friendservice: FriendService by lazy { defaultFriendService(authToken) }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        var friendlist by mutableStateOf<List<UserWithLocationModel>>(emptyList())
        val call = friendservice.getNearbyFriends()

        call.enqueue(object : Callback<List<UserWithLocationModel>> {
            override fun onResponse(
                call: Call<List<UserWithLocationModel>>,
                response: Response<List<UserWithLocationModel>>
            ) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        friendlist = response.body() ?: emptyList()

                    }
                } else {
                    // Handle unsuccessful response
                }
            }

            override fun onFailure(call: Call<List<UserWithLocationModel>>, t: Throwable) {
                runOnUiThread {
                    friendlist = defaultfriendList

                }
            }
        })
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (friendlist.isEmpty()) {
                        LoadingIndicator()
                    } else {
                        FriendList(friendlist)
                    }
                    FriendList(friendlist)


                }
            }
        }


    }
}


fun defaultFriendService(authToken: String): FriendService {
    val retrofit = createAuthenticatedRetrofit(authToken)
    return retrofit.create(FriendService::class.java)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBar() {
    var buttonClicked by remember { mutableStateOf(false) }
    var context = LocalContext.current
    val activity = LocalContext.current as? ComponentActivity

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        onClick = {
                            activity?.finish()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "뒤로 가기")
                    }

                    Icon(imageVector = Icons.Default.Person, contentDescription = "프로필")
                    Text(text = "My Name",Modifier.offset(5.dp,0.dp))


//                    // 돋보기 눌렀을 때 친구검색 창 띄우기 , Composable 함수를 호출하기 위해 buttonClicked = true
//                    IconButton(
//                        onClick = {
//                            buttonClicked = true
//                            showToast(context, "돋보기 클릭")
//                        }
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Search,
//                            contentDescription = "Localized description",
//                            Modifier.offset(140.dp,0.dp)
//                        )
//                    }
                },

                floatingActionButton = {
                    Row() {
                        FloatingActionButton(
                            onClick = {
                                buttonClicked = true
                                showToast(context, "돋보기 완료!")
                            },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(Icons.Default.Search, "Localized description")
                        }

                        FloatingActionButton(
                            onClick = { /*  +버튼 눌렀을 때 친구추가 작업 */
                                showToast(context, "친구추가 완료!")
                            },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(Icons.Filled.Add, "Localized description")
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        Text(
            modifier = Modifier.padding(innerPadding),
            text = ""
        )
    }
    if(buttonClicked){
        SearchFriend()
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFriend(){
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    Column {
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
}

fun showToast(context: android.content.Context, message: String) {
    android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
}


data class Friend(val name: String)

val defaultfriendList = listOf(
    UserWithLocationModel(1, "Alice", "Sha@snu.ac.kr", 125.9, 110.2),
    UserWithLocationModel(2, "Bob", "sha@snu.ac.kr", 11.3, 119.2),
//
//    Friend("Charlie"),
//    Friend("David"),
//    Friend("Apple"),
//    Friend("Banana"),
//    Friend("WaterMelon"),
//    Friend("Maria"),
//    Friend("Anna"),
//    Friend("Bips"),
//    Friend("Yangki"),
//    Friend("Tristana"),
//    Friend("Meliodas"),
//    Friend("Ban")
)

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
        Box(modifier = Modifier.fillMaxWidth()
            .height(60.dp)){
            BottomAppBar()
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