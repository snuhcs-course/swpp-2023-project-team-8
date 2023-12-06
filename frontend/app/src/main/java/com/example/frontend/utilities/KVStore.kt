package com.example.frontend.utilities

/*
 * Abstract interface for KV store
 */
interface KVStore {
    fun getString(key: String): String?
    fun putString(key: String, value: String)

    fun getInt(key: String): Int?
    fun putInt(key: String, value: Int)

    fun getBoolean(key: String): Boolean?
    fun putBoolean(key: String, value: Boolean)

    fun clear()

    companion object {
        const val DEFAULT_INT = -1
    }
}
