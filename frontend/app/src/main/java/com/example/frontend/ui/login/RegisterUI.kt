package com.example.frontend.ui.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend.api.AuthAPI
import com.example.frontend.model.EmailModel
import com.example.frontend.model.RegisterModel
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.ui.theme.Purple80
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUI(onSwitchToLogin: () -> Unit) {
    var context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var response = remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "이메일을 인증해주세요.",
            style = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFFA6A6A6),

                ),
            modifier = Modifier
                .width(180.dp)
                .height(40.dp)
                .offset(x = (-80).dp)
        )

        EmailAuthenticationField(
            email = email,
            onEmailChanged = { newEmail -> email = newEmail },
            onSendClicked = { sendButtonHandler(context, email, response) },
        )

        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Code") },
            modifier = Modifier
                .offset(x = (-35).dp)
                .background(Color.Transparent)
                .width(255.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .offset(x = (-35).dp)
                .background(colorScheme.background)
                .width(255.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .offset(x = (-35).dp)
                .background(colorScheme.background)
                .width(255.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(30.dp))
        CustomButton(
            buttonText = "Register",
            onClickHandler = {
                registerButtonHandler(
                    context,
                    email,
                    code,
                    name,
                    password,
                    response,
                    onSwitchToLogin
                )
            }
        )

    }
}

fun sendButtonHandler(
    context: Context,
    email: String,
    result: MutableState<String>,
    authAPI: AuthAPI = defaultAuthAPI()
) {
    val emailModel = EmailModel(email)
    val call = authAPI.verifyEmail(emailModel)
    call!!.enqueue(object : Callback<EmailModel?> {
        override fun onResponse(call: Call<EmailModel?>, response: Response<EmailModel?>) {
            result.value = "Response Code: " + response.code()
            Toast.makeText(context, "이메일을 확인해주세요", Toast.LENGTH_LONG).show()
        }

        override fun onFailure(call: Call<EmailModel?>, t: Throwable) {
            result.value = "Error: " + t.message
            Toast.makeText(context, result.value, Toast.LENGTH_LONG).show()
        }
    })
}

fun registerButtonHandler(
    context: Context,
    email: String,
    code: String,
    name: String,
    password: String,
    result: MutableState<String>,
    onSwitchToLogin: () -> Unit,
    authAPI: AuthAPI = defaultAuthAPI()
) {
    val registerModel = RegisterModel(email, code, name, password)
    val call = authAPI.register(registerModel)
    call!!.enqueue(object : Callback<RegisterModel?> {
        override fun onResponse(call: Call<RegisterModel?>, response: Response<RegisterModel?>) {
            result.value = "Response Code: " + response.code()
            Toast.makeText(context, "회원가입 성공!", Toast.LENGTH_LONG).show()
            onSwitchToLogin()
        }

        override fun onFailure(call: Call<RegisterModel?>, t: Throwable) {
            result.value = "Error: " + t.message
            Toast.makeText(context, result.value, Toast.LENGTH_LONG).show()
        }
    })
}

@Preview(showBackground = true)
@Composable
fun RegisterUIPreview() {
    FrontendTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorScheme.background
        ) {
            RegisterUI {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailAuthenticationField(
    email: String,
    onEmailChanged: (String) -> Unit,
    onSendClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 42.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = { Text("Email Address") },
            modifier = Modifier
                .padding(end = 8.dp) // Add some padding to separate from the button
                .width(255.dp)
        )

        Button(
            onClick = onSendClicked,
            modifier = Modifier
                .align(Alignment.CenterVertically) // Center the button vertically inside the Row
            , colors = ButtonDefaults.buttonColors(Purple80)
        ) {
            Text(text = "Send")
        }
    }
}
