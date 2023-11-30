package com.example.frontend.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend.ui.login.component.TextButton

/**
 * 로그인 화면에서 보여줄 컴포넌트를 정의합니다.
 */
enum class LoginScreen(
    val title: String,
) {
    LOGIN("Login"),
    REGISTER("Register")
}

@Composable
fun LoginScreen() {
    val currentComponent = remember { mutableStateOf(LoginScreen.LOGIN) }
    val onLoginClick = { currentComponent.value = LoginScreen.LOGIN }
    val onRegisterClick = { currentComponent.value = LoginScreen.REGISTER }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = currentComponent.value.title,
            style = TextStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier
                .width(284.dp)
                .height(50.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            TextButton(
                text = LoginScreen.LOGIN.title,
                onClick = { onLoginClick() },
                isEnabled = currentComponent.value == LoginScreen.LOGIN,
            )
            Spacer(modifier = Modifier.width(20.dp))
            TextButton(
                text = LoginScreen.REGISTER.title,
                onClick = { onRegisterClick() },
                isEnabled = currentComponent.value == LoginScreen.REGISTER,
            )
        }

        if (true) {
            Text(text = "호에엥")
        } else {
            Text(text = "ㅎ이잉잉")
        }
    }
}

@Preview
@Composable
fun LoginNavigationPreview() {
    LoginScreen()
}
