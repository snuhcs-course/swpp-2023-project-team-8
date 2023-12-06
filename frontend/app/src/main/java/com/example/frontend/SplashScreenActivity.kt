package com.example.frontend

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.repository.UserContextRepository
import com.example.frontend.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userContextRepository = UserContextRepository.ofContext(this)

        if (userContextRepository.getIsLoggedIn()) {
            // User is already logged in, skip the login activity
            startActivity(Intent(this, MapActivity::class.java))
        } else {
            // User is not logged in, go to the login activity
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish() // Finish SplashActivity so it's not in the back stack
    }
}
