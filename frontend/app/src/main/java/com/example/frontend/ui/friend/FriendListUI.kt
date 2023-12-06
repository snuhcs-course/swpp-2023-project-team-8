package com.example.frontend.ui.friend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.frontend.model.UserModel
import com.example.frontend.repository.UsersViewModel

@Composable
fun FriendListUI(viewModel: UsersViewModel) {
    val friends by viewModel.friends.observeAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.getFriends()
    }

    LazyColumn {
        items(friends) { friend ->
            FriendItem(friend)
        }
    }
}

@Composable
fun FriendItem(friend: UserModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = friend.name)
    }
}