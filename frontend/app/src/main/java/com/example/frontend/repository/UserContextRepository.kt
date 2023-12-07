package com.example.frontend.repository

import android.content.Context
import com.example.frontend.utilities.EncryptedSharedPreferenceKVStore
import com.example.frontend.utilities.KVStore
import com.example.frontend.utilities.SharedPreferenceKVStore


class UserContextRepository(
    private val store: KVStore
) {
    fun getUserName(): String {
        return store.getString("USERNAME") ?: DEFAULT_USERNAME
    }

    fun saveUserName(newName: String) {
        store.putString(USERNAME, newName)
    }

    fun getUserMail(): String {
        return store.getString(USER_MAIL) ?: DEFAULT_USER_MAIL
    }

    fun getAuthToken(): String {
        return store.getString(AUTH_TOKEN) ?: ""
    }

    fun saveAuthToken(token: String) {
        store.putString(AUTH_TOKEN, token)
    }

    fun saveSelectedPredefinedImage(imageId: Int?) {
        if (imageId != null && imageId != KVStore.DEFAULT_INT) {
            store.putInt(SELECTED_PREDEFINED_IMAGE, imageId)
        }
    }

    fun getSelectedPredefinedImage(): Int? {
        val imageId = store.getInt(SELECTED_PREDEFINED_IMAGE)
        return if (imageId != KVStore.DEFAULT_INT) imageId else null
    }

    fun saveUserMail(userMail: String) {
        store.putString(USER_MAIL, userMail)
    }

    fun saveIsLoggedIn(isLoggedIn: Boolean) {
        store.putBoolean(IS_LOGGED_IN, isLoggedIn)
    }
    fun saveUserId(userId: Int) {
        store.putInt(USER_ID, userId)
    }
    fun getUserId():Long{
        return store.getLong(USER_ID)
    }

    fun getIsLoggedIn(): Boolean {
        return store.getBoolean(IS_LOGGED_IN) ?: false
    }



    fun clear() {
        store.clear()
    }

    companion object {
        /*
         * Returns UserContextRepository instance backed by SharedPreferenceKVStore.
         */
        fun ofContext(context: Context, secure: Boolean = false): UserContextRepository {
            val store = if (secure)
                EncryptedSharedPreferenceKVStore(context)
            else
                SharedPreferenceKVStore(context)
            return UserContextRepository(store)
        }

        private const val USERNAME = "USERNAME"
        const val DEFAULT_USERNAME = "User0"
        private const val USER_MAIL = "USER_MAIL"
        private const val USER_ID = "USER_ID"
        const val DEFAULT_LONG = 1L
        const val DEFAULT_USER_MAIL = "shaf@snu.ac.kr"
        private const val AUTH_TOKEN = "AUTH_TOKEN"
        private const val SELECTED_PREDEFINED_IMAGE = "SELECTED_PREDEFINED_IMAGE"
        private const val IS_LOGGED_IN = "IS_LOGGED_IN"

    }
}
