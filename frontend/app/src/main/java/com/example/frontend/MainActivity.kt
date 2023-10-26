package com.example.frontend


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.frontend.api.AuthAPI
import com.example.frontend.model.LoginModel
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.ui.theme.LightPurple
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
                    LoginUI(this)
                }
            }
        }

    }

    fun moveToMainVeiw(){
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }

}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginUI(context: Context) {
    var context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var response = remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Login",
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
                    color = Color(0xFFDFD5EC),
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
                    color = Color(0xFFA6A6A6),
                ),
                modifier = Modifier
                    .width(90.dp)
                    .height(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(85.dp))
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
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = LightPurple),
            onClick = { loginButtonHandler(context,email,password,response) }, modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    spotColor = LightPurple,
                    ambientColor = LightPurple
                )
                .width(318.dp)
                .height(55.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Login", fontSize = 20.sp)
        }


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
            LoginUI(LocalContext.current)
        }
    }
}

fun loginButtonHandler(context: Context, email: String, password: String, result: MutableState<String>) {
    var url = "http://10.0.2.2:3000"
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val authAPI = retrofit.create(AuthAPI::class.java)
    val loginModel = LoginModel(email,password)
    val call = authAPI.login(loginModel)
    ///////////////////////////////////////////////////////////
    // Login 기능 완성 전에 CheckInActivity로 넘어가기
    val nextIntent = Intent(context, MapActivity::class.java)
    context.startActivity(nextIntent)
    ////////////////////////////////////////////////////////////
    call!!.enqueue(object : Callback<LoginModel?> {
        override fun onResponse(call: Call<LoginModel?>, response: Response<LoginModel?>) {
            result.value = "Response Code: " + response.code()
            Toast.makeText(context,result.value,Toast.LENGTH_LONG).show()
            val nextIntent = Intent(context, MapActivity::class.java)
            context.startActivity(nextIntent)
        }

        override fun onFailure(call: Call<LoginModel?>, t: Throwable) {
            result.value = "Error: " + t.message
            Toast.makeText(context,result.value,Toast.LENGTH_LONG).show()
        }
    })
}