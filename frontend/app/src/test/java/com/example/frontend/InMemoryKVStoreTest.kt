package com.example.frontend


import com.example.frontend.utilities.InMemoryKVStore
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull

class InMemoryKVStoreTest {
    private lateinit var kvStore: InMemoryKVStore

    @Before
    fun setup() {
        kvStore = InMemoryKVStore()
    }

    @Test
    fun `getString returns null when key does not exist`() {
        assertNull(kvStore.getString("nonexistentKey"))
    }

    @Test
    fun `getString returns value when key exists`() {
        kvStore.putString("key", "value")
        assertEquals("value", kvStore.getString("key"))
    }

    @Test
    fun `putString stores value correctly`() {
        kvStore.putString("key", "value")
        assertEquals("value", kvStore.getString("key"))
    }

    @Test
    fun `getInt returns null when key does not exist`() {
        assertNull(kvStore.getInt("nonexistentKey"))
    }

    @Test
    fun `getInt returns value when key exists`() {
        kvStore.putInt("key", 123)
        assertEquals(123, kvStore.getInt("key"))
    }

    @Test
    fun `putInt stores value correctly`() {
        kvStore.putInt("key", 123)
        assertEquals(123, kvStore.getInt("key"))
    }

    @Test
    fun `getBoolean returns null when key does not exist`() {
        assertNull(kvStore.getBoolean("nonexistentKey"))
    }

    @Test
    fun `getBoolean returns value when key exists`() {
        kvStore.putBoolean("key", true)
        assertEquals(true, kvStore.getBoolean("key"))
    }
}
