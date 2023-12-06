package com.example.frontend.utilities

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/*
 * KVStore implementation using SharedPreference, which is encrypted
 */
class EncryptedSharedPreferenceKVStore(context: Context) : SharedPreferenceKVStore(context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    override val appPrefs: SharedPreferences =
        EncryptedSharedPreferences.create(
            context,
            APP_PREFS,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    companion object {
        private const val APP_PREFS = "secure_app_prefs"
    }
}
