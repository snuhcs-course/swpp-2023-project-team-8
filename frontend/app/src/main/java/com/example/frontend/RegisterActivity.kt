package com.example.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.register.ui.theme.Purple80
import com.example.register.ui.theme.RegisterTheme


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme.background
                ) {
                    RegisterUI()
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUI() {
//    var context = LocalContext.current
    var Email by remember { mutableStateOf("") }
    var Password by remember { mutableStateOf("") }
//    var Response = remember { mutableStateOf("") }
    var Name by remember { mutableStateOf("") }
    var Code by remember { mutableStateOf("") }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Register",
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
        Row {
            Text(
                text = "Login",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFA6A6A6),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .width(90.dp)
                    .height(40.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Register",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFDFD5EC),
                ),
                modifier = Modifier
                    .width(90.dp)
                    .height(40.dp)
            )

        }
        Spacer(modifier = Modifier.height(30.dp))
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
                .offset(x = -80.dp)
        )

        Row {
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                value = Email,
                onValueChange = { Email = it },
                label = { Text("Email Address") },
                modifier = Modifier
                    .offset(x = -3.dp)
                    .background(MaterialTheme.colorScheme.background)
            )
            Chip()
        }



        TextField(
            value = Code,
            onValueChange = { Code = it },
            label = { Text("Code") },
            modifier = Modifier
                .offset(x = -35.dp)
                .background(Color.Transparent)
        )


        TextField(
            value = Name,
            onValueChange = { Name = it },
            label = { Text("Name") },
            modifier = Modifier
                .offset(x = -35.dp)
                .background(MaterialTheme.colorScheme.background)
        )


        TextField(
            value = Password,
            onValueChange = { Password = it },
            label = { Text("Password") } ,
            modifier = Modifier
                .offset(x = -35.dp)
                .background(MaterialTheme.colorScheme.background),
            visualTransformation = PasswordVisualTransformation()
        )


        TextField(
            value = Password,
            onValueChange = { Password = it },
            label = { Text("Password Confirm") },
            modifier = Modifier
                .offset(x = -35.dp)
                .background(MaterialTheme.colorScheme.background),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(30.dp))
        RegisterButton {
            //수행할 동작 작성
        }

    }
}

@Composable
fun RegisterButton(onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(Purple80),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = "Register",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFFA6A6A6),
            )
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun Chip() {
    var selected by remember { mutableStateOf(false) }

    FilterChip(
        onClick = { selected = !selected },
        label = {
            Text("Send")
        },
        selected = selected,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
    )
}