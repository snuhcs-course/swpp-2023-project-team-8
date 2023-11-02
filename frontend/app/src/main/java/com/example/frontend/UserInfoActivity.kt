package com.example.frontend

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.frontend.ui.theme.FrontendTheme

class UserInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserInfoUI("Android")
                }
            }
        }
    }
}

@Composable
fun UserInfoUI(name: String, modifier: Modifier = Modifier) {
    var context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(color = Color(0xFFFEF7FF))
            .padding(4.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 28.dp) // Add left padding to the Box
                .padding(top = 4.3.dp)
        ) {
            Text(
                text = "내 정보",
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF1D1B20)
                )
            )
        }
    }

    Row(

    ) {

        Icon(
            imageVector = Icons.Outlined.KeyboardArrowLeft,
            contentDescription = null,
            modifier = Modifier
                .padding(start =0.dp)
                .padding(top = 3.dp)
                .size(46.dp)
                .clickable {
                    val nextIntent = Intent(context, MapActivity::class.java)
                    context.startActivity(nextIntent)
                }
            
        )
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 300.dp)
                .padding(top = 3.dp)
                .size(46.dp)
        )



    }
}



@Preview(showBackground = true)
@Composable
fun UserInfoPreview() {
    FrontendTheme {
        UserInfoUI("Android")
    }
}