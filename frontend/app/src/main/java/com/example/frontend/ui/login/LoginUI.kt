package com.example.frontend.ui.login

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.frontend.api.AuthService
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.ui.theme.LightPurple
import com.example.frontend.ui.theme.Purple80
import com.example.frontend.usecase.loginUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginUI() {
    var context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var response = remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(140.dp))
        CustomButton(
            buttonText = "Login",
            onClickHandler = { loginUseCase(context, email, password, response) })
    }
}

@Preview(showBackground = true)
@Composable
fun LoginUIPreview() {
    FrontendTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginUI()
        }
    }
}

// To save the auth token securely when logging in
fun saveAuthToken(context: Context, authToken: String, userName: String?) {
    val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_app_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // Save auth token securely
    with(sharedPreferences.edit()) {
        putString("AUTH_TOKEN", authToken)
        putString("USERNAME", userName)
        apply()
    }

    // Update login state
    val appPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    with(appPrefs.edit()) {
        putBoolean("IS_LOGGED_IN", true)
        putString("USERNAME", userName)
        apply()
    }
}

fun getUsername(context: Context): String? {
    val appPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return appPrefs.getString("USERNAME", "User0")
}


fun getAuthtoken(context: Context): String {
    val appPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return appPrefs.getString("AUTH_TOKEN", "")?:""
}
fun defaultAuthService(): AuthService {
    var url = "http://10.0.2.2:3000"
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(AuthService::class.java)
}

@Composable
fun CustomButton(
    buttonText: String,
    onClickHandler: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(

        colors = ButtonDefaults.buttonColors(Purple80),
        onClick = onClickHandler,
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                spotColor = LightPurple,
                ambientColor = LightPurple
            )
            .width(318.dp)
            .height(55.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = buttonText, fontSize = 20.sp)
    }
}
