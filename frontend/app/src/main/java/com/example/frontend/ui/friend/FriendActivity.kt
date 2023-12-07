package com.example.frontend.ui.friend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontend.ui.component.MyTopAppBar
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.ui.theme.Purple40
import com.example.frontend.ui.theme.Purple80
import com.example.frontend.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FriendActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: UsersViewModel = viewModel()
            FrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FriendUI(viewModel)
                }
            }
        }
    }
}

@Composable
fun FriendUI(viewModel: UsersViewModel) {
    var selectedTab by remember { mutableStateOf(TabItem.Friends) }

    Column {
        MyTopAppBar(title = "친구")

        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            contentColor = Purple40,
        ) {
            TabItem.values().forEach { tab ->
                Tab(
                    selected = tab == selectedTab,
                    onClick = { selectedTab = tab },
                    text = { Text(tab.title) },
                    selectedContentColor = Purple40,
                    unselectedContentColor = Purple80
                )
            }
        }

        // Content of the selected tab
        when (selectedTab) {
            TabItem.Friends -> FriendListUI(viewModel)
            TabItem.FriendRequests -> FriendRequestUI(viewModel)
            TabItem.Users -> UserListUI(viewModel)
        }
    }
}

enum class TabItem(val title: String) {
    Friends("친구 목록"),
    FriendRequests("친구 요청"),
    Users("친구 찾기")
}
