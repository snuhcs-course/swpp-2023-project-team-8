package com.example.frontend.utilities

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Provides a singleton instance of Gson.
 * Convert rails snake_case to kotlin camelCase
 */
object GsonProvider {
    val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
}