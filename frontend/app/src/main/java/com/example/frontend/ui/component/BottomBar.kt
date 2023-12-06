package com.example.frontend.ui.component

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frontend.IconToggleButton
import com.example.frontend.MeetupActivity
import com.example.frontend.MeetupListUI
import com.example.frontend.MissionActivity
import com.example.frontend.ui.friend.FriendActivity
import com.example.frontend.ui.settings.UserInfoActivity
import com.google.android.gms.maps.model.LatLng

@Composable
fun BottomBar(currentLocation: LatLng?) {
    val context = LocalContext.current
    val icons = listOf(
        Icons.Default.Star,
        Icons.Outlined.AccountBox,
        Icons.Outlined.DateRange,
        Icons.Outlined.CheckCircle,
        Icons.Outlined.Settings,
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color(0xFFF3EDF7))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 37.dp, end = 37.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            icons.forEach { icon ->
                IconToggleButton(icon = icon) {
                    when (icon) {
                        icons[0] -> {
                            // MeetUp 생성으로 이동
                            val nextIntent = Intent(context, MeetupActivity::class.java)
                            nextIntent.putExtra("currentLocation", currentLocation)
                            context.startActivity(nextIntent)
                        }

                        icons[1] -> {
                            // Friend List 이동
                            val nextIntent = Intent(context, FriendActivity::class.java)
                            context.startActivity(nextIntent)
                        }

                        icons[2] -> {
                            // 약속 list
                            val nextIntent = Intent(context, MeetupListUI::class.java)
                            context.startActivity(nextIntent)
                        }

                        icons[3] -> {
                            // MissionActivity 이동
                            val nextIntent = Intent(context, MissionActivity::class.java)
                            context.startActivity(nextIntent)
                        }

                        icons[4] -> {
                            // userInfo로 이동
                            val nextIntent = Intent(context, UserInfoActivity::class.java)
                            context.startActivity(nextIntent)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun BottomBarPreview() {
    BottomBar(null)
}
