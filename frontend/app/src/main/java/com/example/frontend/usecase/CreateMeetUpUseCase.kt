package com.example.frontend.usecase

import android.content.Context
import android.util.Log
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
                    Log.d("meetup", "SUcces!!")
                } else {
                    Log.d("meetup", "no success!!")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("meetup", "fail!!")
            }
        })
    }
}