package com.example.frontend.utilities

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
}
