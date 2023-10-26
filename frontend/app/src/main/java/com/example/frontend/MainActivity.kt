package com.example.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.frontend.ui.theme.FrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthenticationUI()
                }
            }
        }
    }
}

enum class CurrentScreen {
    LOGIN, REGISTER
}

@Composable
fun AuthenticationUI() {
    var currentScreen by remember { mutableStateOf(CurrentScreen.LOGIN) }

    // This is the onSwitchToRegister function
    val onSwitchToRegister: () -> Unit = {
        currentScreen = CurrentScreen.REGISTER
    }

    // This function switches to the Login screen
    val onSwitchToLogin: () -> Unit = {
        currentScreen = CurrentScreen.LOGIN
    }

    when (currentScreen) {
        CurrentScreen.LOGIN -> LoginUI(onSwitchToRegister)
        CurrentScreen.REGISTER -> RegisterUI(onSwitchToLogin)
    }
}
