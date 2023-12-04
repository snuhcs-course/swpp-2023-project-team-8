package com.example.frontend.repository

import android.content.Context

class UserContextRepository(
    private val context: Context
) {
    private val appPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun getUserName(): String {
        val defaultValue = "User0"
        return appPrefs.getString("USERNAME", defaultValue) ?: defaultValue
    }

    fun getAuthToken(): String {
        return appPrefs.getString("AUTH_TOKEN", "") ?: ""
    }
}