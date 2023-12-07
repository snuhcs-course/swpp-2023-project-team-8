package com.example.frontend.ui.component

import android.app.Activity
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frontend.ui.theme.LightPurple
import com.example.frontend.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetUpTopAppBar(
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF3EDF7),
            titleContentColor = Color(0xFF000000)
        ),
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            Button(
                onClick = {
                    if (context is Activity) {
                        context.finish()
                    }
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp)
                    .padding(start = 16.dp, top = 16.dp),
                colors = ButtonDefaults.buttonColors(Purple80)
            ) {
                Text(text = "취소")
            }
        },
        scrollBehavior = scrollBehavior,
        actions = actions,
    )
}

@Preview
@Composable
private fun PreviewMyTopAppBar() {
    MyTopAppBar(
        title = "Meetup",
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}
