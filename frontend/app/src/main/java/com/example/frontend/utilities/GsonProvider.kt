package com.example.frontend.utilities

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import com.google.gson.JsonPrimitive
import java.time.ZoneOffset

/**
 * Provides a singleton instance of Gson.
 * Convert rails snake_case to kotlin camelCase
 */
object GsonProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    val localDateTimeDeserializer = JsonDeserializer { json, _, _ ->
        val dateTimeString = json.asJsonPrimitive.asString
        val instant = Instant.parse(dateTimeString)
        LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val localDateTimeSerializer = JsonSerializer<LocalDateTime> { src, _, _ ->
        val zonedDateTime = src.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC)
        JsonPrimitive(zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(LocalDateTime::class.java, localDateTimeDeserializer)
        .registerTypeAdapter(
            LocalDateTime::class.java,
            localDateTimeSerializer
        ) // for serialization
        .create()
}