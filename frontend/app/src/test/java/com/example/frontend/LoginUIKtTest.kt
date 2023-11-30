package com.example.frontend

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.example.frontend.api.AuthAPI
import com.example.frontend.model.AuthResponse
import com.google.android.gms.maps.model.LatLng
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
    lateinit var mockCall: Call<AuthResponse?>

    @Before
    fun setup() {
        `when`(mockAuthAPI.login(any())).thenReturn(mockCall)
    }

    @Test
    fun loginButtonHandler() {
        val result = mutableStateOf("")

        com.example.frontend.ui.login.loginButtonHandler(mockContext, "test@example.com", "password", result, mockAuthAPI)

        // Capture the callback
        val argumentCaptor =
            ArgumentCaptor.forClass(Callback::class.java as Class<Callback<AuthResponse?>>)
        verify(mockCall).enqueue(argumentCaptor.capture())

        // Simulate a successful response
        val response = Response.success(AuthResponse("token", "username", LatLng(0.0, 0.0)))
//        TODO: 테스트 수정
//        argumentCaptor.value.onResponse(mockCall, response)
//
//        // Check the result
//        assertEquals("Response Code: 200", result.value)
    }
}
