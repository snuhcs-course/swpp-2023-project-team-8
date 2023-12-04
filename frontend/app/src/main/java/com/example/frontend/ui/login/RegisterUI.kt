package com.example.frontend.ui.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frontend.api.AuthService
import com.example.frontend.model.EmailModel
import com.example.frontend.model.RegisterModel
import com.example.frontend.ui.component.CustomButton
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

        // TODO(heka1024): 키보드 입력 창에서 '다음' 버튼 등으로 넘어갈 수 있게 하기
        // TODO(heka1024): 터치할 때 키보드 내려가게 하게
        // 눌러서 내려가는 건
        // 다음을 띄우려면
        // imeAction = ImeAction.Next
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
    authService: AuthService = AuthService.create()
) {
    val emailModel = EmailModel(email)
//    val call = authService.verifyEmail(emailModel)
//    call!!.enqueue(object : Callback<EmailModel?> {
//        override fun onResponse(call: Call<EmailModel?>, response: Response<EmailModel?>) {
//            result.value = "Response Code: " + response.code()
//            Toast.makeText(context, "이메일을 확인해주세요", Toast.LENGTH_LONG).show()
//        }
//
//        override fun onFailure(call: Call<EmailModel?>, t: Throwable) {
//            result.value = "Error: " + t.message
//            Toast.makeText(context, result.value, Toast.LENGTH_LONG).show()
//        }
//    })
}

fun registerButtonHandler(
    context: Context,
    email: String,
    code: String,
    name: String,
    password: String,
    result: MutableState<String>,
    onSwitchToLogin: () -> Unit,
    authService: AuthService = AuthService.create()
) {
    val registerModel = RegisterModel(email, code, name, password)
    val call = authService.register(registerModel)
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
