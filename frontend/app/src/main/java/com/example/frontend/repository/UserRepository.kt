package com.example.frontend.repository

import android.content.Context
import com.example.frontend.R

val predefinedImages = listOf(
    R.drawable.cat,
    R.drawable.cat_sunglass,
    R.drawable.dog_sunglass,
    R.drawable.hamster
)

class UserContextRepository(
    private val context: Context
) {
    private val appPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun getUserName(): String {
        val defaultValue = "User0"
        return appPrefs.getString("USERNAME", defaultValue) ?: defaultValue
    }
    fun saveUserName(newName: String?) {
        val editor = appPrefs.edit()
        editor.putString("USERNAME", newName)
        editor.apply()
    }

    fun getUserMail(): String {
        val defaultValue = "sha@snu.ac.kr"
        return appPrefs.getString("USER_MAIL", defaultValue) ?: defaultValue
    }

    fun getAuthToken(): String {
        return appPrefs.getString("AUTH_TOKEN", "") ?: ""
    }
    fun saveSelectedPredefinedImage(imageId: Int?) {
        val editor = appPrefs.edit()
        editor.putInt("SELECTED_PREDEFINED_IMAGE", imageId ?: -1)
        editor.apply()
    }

    fun getSelectedPredefinedImage(): Int? {
        val imageId = appPrefs.getInt("SELECTED_PREDEFINED_IMAGE", -1)
        return if (imageId != -1) imageId else null
    }

}