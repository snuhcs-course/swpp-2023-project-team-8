package com.example.frontend.usecase

import android.content.Context
import android.util.Log
import com.example.frontend.api.CheckInService
import com.example.frontend.model.CheckInModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckInUseCase(context: Context) {
    private val checkInService = CheckInService.create(context)

    fun execute(latitude: Double, longitude: Double) {
        val checkInModel = CheckInModel(latitude, longitude)
        checkInService.login(checkInModel)?.enqueue(object : Callback<CheckInModel?> {
            override fun onResponse(call: Call<CheckInModel?>, response: Response<CheckInModel?>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("CheckInUseCase", "Check in successful: $body")
                } else {
                    Log.d("CheckInUseCase", "Check in failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CheckInModel?>, t: Throwable) {
                Log.d("CheckInUseCase", "Check in failed: ${t.message}")
            }
        })
    }
}
