package com.example.frontend.utilities

import android.content.Context
import android.content.SharedPreferences

/*
 * KVStore implementation using SharedPreference
 */
class SharedPreferenceKVStore(context: Context) : KVStore {
    private val appPrefs: SharedPreferences =
        context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)

    override fun getString(key: String): String? {
        return appPrefs.getString(key, null)
    }

    override fun putString(key: String, value: String) {
        val editor = appPrefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun getInt(key: String): Int? {
        return appPrefs.getInt(key, KVStore.DEFAULT_INT)
    }

    override fun putInt(key: String, value: Int) {
        val editor = appPrefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    companion object {
        const val APP_PREFS = "app_prefs"
    }
}
