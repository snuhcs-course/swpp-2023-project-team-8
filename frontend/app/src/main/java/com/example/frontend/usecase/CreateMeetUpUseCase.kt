package com.example.frontend.usecase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.frontend.api.MeetUpService
import com.example.frontend.data.defaultMeetups
import com.example.frontend.model.MeetupModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import okhttp3.Request

class CreateMeetUpUseCase(
    private val context: Context,
    private val meetUpService: MeetUpService = MeetUpService.create(context)
) {
    fun send(meetup: MeetupModel) {
        val call = meetUpService.createMeetUp(meetup)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    var result = "Create MeetUp Success!! "
                    Toast.makeText(context, result, Toast.LENGTH_LONG).show()
                } else {
                    Log.d("meetup", response.errorBody().toString())
                    var result = "Create MeetUp failed: " + response.errorBody()?.string()
                    Toast.makeText(context, result, Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("meetup", "fail!!${t.message}")
                var result = "Create MeetUp error: " + t.message
                Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            }
        })
    }
}