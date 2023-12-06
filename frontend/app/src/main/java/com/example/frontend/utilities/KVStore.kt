package com.example.frontend.utilities

/*
 * Abstract interface for KV store
 */
interface KVStore {
    fun getString(key: String): String?
    fun putString(key: String, value: String)

    fun getInt(key: String): Int?
    fun putInt(key: String, value: Int)
}
