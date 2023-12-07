package com.example.frontend.usecase

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.frontend.api.MeetUpService
import com.example.frontend.data.defaultMeetups
import com.example.frontend.model.MeetUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListMeetUpUseCase(
    private val context: Context,
    private val meetUpService: MeetUpService = MeetUpService.create(context)
) {
    fun fetch(onMeetUpsFetched: (List<MeetUpResponse>) -> Unit) {
        val call = meetUpService.getMeetUps()

        call.enqueue(object : Callback<List<MeetUpResponse>> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<List<MeetUpResponse>>,
                response: Response<List<MeetUpResponse>>
            ) {
                if (response.isSuccessful) {
                    val missions = response.body() ?: emptyList()
                    onMeetUpsFetched(missions)
                } else {
                    var result = "List MeetUp failed: " + response.errorBody()?.string()
                    Toast.makeText(context, result, Toast.LENGTH_LONG).show()
                }
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFailure(call: Call<List<MeetUpResponse>>, t: Throwable) {
                var result = "List MeetUp error: " + t.message
                Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            }
        })
    }
}