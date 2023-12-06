package com.example.frontend.usecase

import android.content.Context
import android.util.Log
import com.example.frontend.api.CheckInService
import com.example.frontend.model.CheckInModel
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/*
 * 체크인을 수행하는 메서드
 */
class CheckInUseCase @Inject constructor(
    @ApplicationContext context: Context
) {
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
