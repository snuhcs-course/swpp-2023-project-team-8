package com.example.frontend.usecase

import android.content.Context
import android.os.Build
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

<<<<<<< HEAD
        call.enqueue(object : Callback<List<MeetupModel>> {
=======
        call.enqueue(object : Callback<List<MeetUpResponse>> {
>>>>>>> 7bd50ee32ea221baa2088386125f02a9aeae52d2
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<List<MeetUpResponse>>,
                response: Response<List<MeetUpResponse>>
            ) {
                if (response.isSuccessful) {
                    val missions = response.body() ?: emptyList()
                    onMeetUpsFetched(missions)
                } else {
                    onMeetUpsFetched(defaultMeetups)
                }
            }

            @RequiresApi(Build.VERSION_CODES.O)
<<<<<<< HEAD
            override fun onFailure(call: Call<List<MeetupModel>>, t: Throwable) {
=======
            override fun onFailure(call: Call<List<MeetUpResponse>>, t: Throwable) {
>>>>>>> 7bd50ee32ea221baa2088386125f02a9aeae52d2
                onMeetUpsFetched(defaultMeetups)
            }
        })
    }
}