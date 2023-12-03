package com.example.frontend

import com.example.frontend.utilities.GsonProvider
import com.google.gson.JsonParser
import org.junit.Assert
import org.junit.Test

class GsonProviderTest {
    data class TestClass(val firstName: String, val lastName: String)

    @Test
    fun `GsonProvider should convert camelCase to snake_case`() {
        val testObject = TestClass("John", "Doe")
        val gson = GsonProvider.gson

        val json = gson.toJson(testObject)
        val jsonObject = JsonParser.parseString(json).asJsonObject

        Assert.assertTrue(jsonObject.has("first_name"))
        Assert.assertTrue(jsonObject.has("last_name"))
        Assert.assertEquals("John", jsonObject.get("first_name").asString)
        Assert.assertEquals("Doe", jsonObject.get("last_name").asString)
    }

    @Test
    fun `GsonProvider should convert snake_case to camelCase`() {
        val jsonString = """{"first_name": "John", "last_name": "Doe"}"""
        val gson = GsonProvider.gson

        val testObject = gson.fromJson(jsonString, TestClass::class.java)

        Assert.assertEquals("John", testObject.firstName)
        Assert.assertEquals("Doe", testObject.lastName)
    }
}