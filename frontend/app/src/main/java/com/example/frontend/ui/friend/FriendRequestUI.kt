package com.example.frontend.ui.friend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.frontend.model.UserModel
import com.example.frontend.ui.theme.Purple80
import com.example.frontend.viewmodel.UsersViewModel

@Composable
fun FriendRequestUI(
    viewModel: UsersViewModel
) {
    val pendingRequests by viewModel.pendingRequests.observeAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.getPendingRequests()
    }

    LazyColumn {
        items(pendingRequests) { request ->
            FriendRequestItem(request) {
                viewModel.confirmRequest(request.id)
            }
        }
    }
}

@Composable
fun FriendRequestItem(
    request: UserModel,
    onConfirm: (UserModel) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = request.name)
        Row {
            Button(
                onClick = { onConfirm(request) },
                colors = ButtonDefaults.buttonColors(Purple80)
            ) {
                Text("Confirm")
            }
        }
    }
}
