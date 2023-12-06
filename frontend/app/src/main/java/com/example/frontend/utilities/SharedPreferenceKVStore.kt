package com.example.frontend.utilities

import android.content.Context
import android.content.SharedPreferences
import com.example.frontend.repository.UserContextRepository

/*
 * KVStore implementation using SharedPreference
 */
open class SharedPreferenceKVStore(context: Context) : KVStore {
    protected open val appPrefs: SharedPreferences =
        context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)

    override fun getString(key: String): String? {
        return appPrefs.getString(key, null)
    }

    override fun putString(key: String, value: String) {
        val editor = appPrefs.edit()
        editor.putString(key, value)
        editor.apply()
    }
    override fun putLong(key: String, value: Long) {
        appPrefs.edit().putLong(key, value).apply()
    }

    override fun getLong(key: String): Long {
        return appPrefs.getLong(key, UserContextRepository.DEFAULT_LONG)
    }

    override fun getInt(key: String): Int? {
        return appPrefs.getInt(key, KVStore.DEFAULT_INT)
    }

    override fun putInt(key: String, value: Int) {
        val editor = appPrefs.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    override fun getBoolean(key: String): Boolean? {
        return appPrefs.getBoolean(key, false)
    }

    override fun putBoolean(key: String, value: Boolean) {
        val editor = appPrefs.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun clear() {
        val editor = appPrefs.edit()
        editor.clear()
        editor.apply()
    }


    companion object {
        const val APP_PREFS = "app_prefs"
    }
}
