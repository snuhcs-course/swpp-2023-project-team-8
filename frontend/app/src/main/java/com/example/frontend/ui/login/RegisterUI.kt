package com.example.frontend.ui.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frontend.api.AuthService
import com.example.frontend.model.EmailModel
import com.example.frontend.model.RegisterModel
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.ui.theme.Purple80
import com.example.frontend.utilities.HttpStatus
import com.example.frontend.utilities.isValidSnuMail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUI(onSwitchToLogin: () -> Unit) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val response = remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }

    val isInitialInput = email.isEmpty() && password.isEmpty()
    val isWaitingForResponse = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(20.dp)
    ) {

        // TODO(heka1024): 키보드 입력 창에서 '다음' 버튼 등으로 넘어갈 수 있게 하기
        // TODO(heka1024): 터치할 때 키보드 내려가게 하게
        // 눌러서 내려가는 건
        // 다음을 띄우려면
        // imeAction = ImeAction.Next
//        EmailAuthenticationField(
//            email = email,
//            onEmailChanged = { newEmail -> email = newEmail },
//            onSendClicked = { sendButtonHandler(context, email, response) },
//        )

        OutlinedTextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            label = { Text("Email Address") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
            },
            isError = !isValidSnuMail(email) && !isInitialInput,
            supportingText = { if (!isValidSnuMail(email) && !isInitialInput) Text("SNU Mail을 입력해주세요") },
        )

        Button(
            onClick = { isWaitingForResponse.value = true; sendButtonHandler(context, email, isWaitingForResponse) },
            colors = ButtonDefaults.buttonColors(Purple80),
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.End),
            enabled = !isWaitingForResponse.value,
        ) { Text(text = "Send") }

        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Code") },
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
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

/*
 * 서버에게 인증 코드를 요청하는 함수
 */
private fun sendButtonHandler(
    context: Context,
    email: String,
    isWaitingForResponse: MutableState<Boolean>,
    authService: AuthService = AuthService.create()
) {
    authService.verifyEmail(EmailModel(email)).enqueue(object : Callback<EmailModel?> {
        override fun onResponse(call: Call<EmailModel?>, response: Response<EmailModel?>) {
            if (HttpStatus.fromCode(response.code()) != HttpStatus.CREATED) {
                Toast.makeText(context, "에러가 발생했어요.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "인증 코드가 전송되었습니다!", Toast.LENGTH_LONG).show()
            }
            isWaitingForResponse.value = false
        }

        override fun onFailure(call: Call<EmailModel?>, t: Throwable) {
            Toast.makeText(context, "에러가 발생했어요.", Toast.LENGTH_LONG).show()
            isWaitingForResponse.value = false
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
    authService: AuthService = defaultAuthService()
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
