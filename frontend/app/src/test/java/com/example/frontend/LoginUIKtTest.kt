package com.example.frontend

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.example.frontend.api.AuthAPI
import com.example.frontend.model.LoginModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class LoginUIKtTest {

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockAuthAPI: AuthAPI

    @Mock
    lateinit var mockCall: Call<LoginModel?>

    @Before
    fun setup() {
        `when`(mockAuthAPI.login(any())).thenReturn(mockCall)
    }

    @Test
    fun loginButtonHandler() {
        val result = mutableStateOf("")

        loginButtonHandler(mockContext, "test@example.com", "password", result, mockAuthAPI)

        // Capture the callback
        val argumentCaptor =
            ArgumentCaptor.forClass(Callback::class.java as Class<Callback<LoginModel?>>)
        verify(mockCall).enqueue(argumentCaptor.capture())

        // Simulate a successful response
        val response = Response.success(LoginModel("test@example.com", "password"))
        argumentCaptor.value.onResponse(mockCall, response)

        // Check the result
        assertEquals("Response Code: 200", result.value)
    }
}