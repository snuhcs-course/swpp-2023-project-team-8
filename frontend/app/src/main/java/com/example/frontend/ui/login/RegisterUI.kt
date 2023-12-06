package com.example.frontend.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frontend.ui.component.CustomButton
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.ui.theme.Purple80
import com.example.frontend.usecase.login.RegisterUseCase
import com.example.frontend.usecase.login.SendVerificationCodeUseCase
import com.example.frontend.utilities.isValidSnuMail

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
        OutlinedTextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            label = { Text("Email Address") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
            },
            isError = !isValidSnuMail(email) && !isInitialInput,
            supportingText = { if (!isValidSnuMail(email) && !isInitialInput) Text("SNU Mail을 입력해주세요") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                isWaitingForResponse.value = true
                SendVerificationCodeUseCase(context, email, isWaitingForResponse).execute()
            },
            colors = ButtonDefaults.buttonColors(Purple80),
            modifier = Modifier
                .align(Alignment.End),
            enabled = !isWaitingForResponse.value,
        ) { Text(text = "Send") }

        // Only number and upper case alphabet
        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Code") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                imeAction = ImeAction.Next,
            ),
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            )
        )

        Spacer(modifier = Modifier.height(30.dp))
        CustomButton(
            buttonText = "Register",
            modifier = Modifier.fillMaxWidth(),
            onClickHandler = {
                RegisterUseCase(context, email, code, name, password, response, onSwitchToLogin)
                    .execute()
            },
        )
    }
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
