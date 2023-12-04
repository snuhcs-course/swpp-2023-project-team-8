package com.example.frontend.ui.settings.component

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend.MissionActivity

@Composable
fun MissionCard() {
    var context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3EDF7),
        ),
        modifier = Modifier
            .padding(top = 50.dp)
            .size(width = 300.dp, height = 500.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row() {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            val nextIntent = Intent(context, MissionActivity::class.java)
                            context.startActivity(nextIntent)
                        }
                        .size(50.dp),

                    tint = (Color(0xFF6750A4))

                )
                Text(
                    text = "달성 기록",
                    color = Color(0xFF000000),
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        letterSpacing = 0.1.sp,
                    ),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(top = 3.dp)
                        .clickable {
                            val nextIntent = Intent(context, MissionActivity::class.java)
                            context.startActivity(nextIntent)
                        }
                )

            }

        }
    }
}

@Composable
@Preview
private fun MissionCardPreview() {
    MissionCard()
}