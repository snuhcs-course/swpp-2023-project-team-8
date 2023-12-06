package com.example.frontend

import android.content.Context
import android.content.SharedPreferences
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.frontend.utilities.SharedPreferenceKVStore
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class SharedPreferenceKVStoreTest {
    private lateinit var kvStore: SharedPreferenceKVStore
    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setup() {
        context = mock(Context::class.java)
        sharedPreferences = mock(SharedPreferences::class.java)
        editor = mock(SharedPreferences.Editor::class.java)

        `when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)
        `when`(editor.putInt(anyString(), anyInt())).thenReturn(editor)

        kvStore = SharedPreferenceKVStore(context)
    }

    @Test
    fun getStringReturnsNullWhenKeyDoesNotExist() {
        `when`(sharedPreferences.getString(anyString(), any())).thenReturn(null)
        assertNull(kvStore.getString("nonexistentKey"))
    }

    @Test
    fun getStringReturnsValueWhenKeyExists() {
        `when`(sharedPreferences.getString(anyString(), any())).thenReturn("value")
        assertEquals("value", kvStore.getString("key"))
    }

    @Test
    fun putStringStoresValueCorrectly() {
        kvStore.putString("key", "value")
        verify(editor).apply()
    }

    @Test
    fun getIntReturnsDefaultWhenKeyDoesNotExist() {
        `when`(sharedPreferences.getInt(anyString(), anyInt())).thenReturn(-1)
        assertEquals(-1, kvStore.getInt("nonexistentKey"))
    }

    @Test
    fun getIntReturnsValueWhenKeyExists() {
        `when`(sharedPreferences.getInt(anyString(), anyInt())).thenReturn(123)
        assertEquals(123, kvStore.getInt("key"))
    }

    @Test
    fun putIntStoresValueCorrectly() {
        kvStore.putInt("key", 123)
        verify(editor).apply()
    }
}
