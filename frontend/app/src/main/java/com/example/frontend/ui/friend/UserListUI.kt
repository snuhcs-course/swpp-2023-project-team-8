package com.example.frontend.ui.friend

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.frontend.model.UserModel
import com.example.frontend.viewmodel.UsersViewModel
import com.example.frontend.ui.theme.Purple80

@Composable
fun UserListUI(viewModel: UsersViewModel, context: Context = LocalContext.current) {
    val users by viewModel.users.observeAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.getUsers()
    }

    val requestStatus by viewModel.requestStatus.observeAsState()

    LaunchedEffect(requestStatus) {
        requestStatus?.let {
            Toast.makeText(context, it.substringBefore("%"), Toast.LENGTH_SHORT).show()
            viewModel.clearRequestStatus()
        }
    }

    var searchQuery by remember { mutableStateOf("") }

    val filteredUsers = users.filter { it.name.contains(searchQuery, ignoreCase = true) }

    // Search bar
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        label = { Text("검색") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )

    LazyColumn {
        items(filteredUsers) { user ->
            UserItem(user) { selectedUser ->
                viewModel.sendFriendRequest(selectedUser.id)
            }
        }
    }
}

@Composable
fun UserItem(user: UserModel, onSendRequest: (UserModel) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = user.name)
        Button(onClick = { onSendRequest(user) }, colors = ButtonDefaults.buttonColors(Purple80)) {
            Text("Send Request")
        }
    }
}
