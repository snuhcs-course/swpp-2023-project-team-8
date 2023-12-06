package com.example.frontend.utilities

import com.example.frontend.repository.UserContextRepository

class InMemoryKVStore : KVStore {
    private val store = HashMap<String, Any>()

    override fun getString(key: String): String? {
        return store[key] as? String
    }

    override fun putString(key: String, value: String) {
        store[key] = value
    }

    override fun getInt(key: String): Int? {
        return store[key] as? Int
    }

    override fun putInt(key: String, value: Int) {
        store[key] = value
    }

    override fun getBoolean(key: String): Boolean? {
        return store[key] as? Boolean
    }

    override fun putBoolean(key: String, value: Boolean) {
        store[key] = value
    }
    override fun putLong(key: String, value: Long) {
        store[key] = value
    }

    override fun getLong(key: String): Long {
        return store[key] as Long
    }

    override fun clear() {
        store.clear()
    }
}
