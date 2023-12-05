package com.example.frontend.usecase

import android.content.Context
import com.example.frontend.api.MeetUpService
import com.example.frontend.data.defaultMeetups
import com.example.frontend.model.MeetupModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListMeetUpUseCase(
    private val context: Context,
    private val meetUpService: MeetUpService = MeetUpService.create()
) {
    fun fetch(onMeetUpsFetched: (List<MeetupModel>) -> Unit) {
        val call = meetUpService.getMeetUps()

        call.enqueue(object : Callback<List<MeetupModel>> {
            override fun onResponse(
                call: Call<List<MeetupModel>>,
                response: Response<List<MeetupModel>>
            ) {
                if (response.isSuccessful) {
                    val missions = response.body() ?: emptyList()
                    onMeetUpsFetched(missions)
                } else {
                    onMeetUpsFetched(defaultMeetups)
                }
            }

            override fun onFailure(call: Call<List<MeetupModel>>, t: Throwable) {
                onMeetUpsFetched(defaultMeetups)
            }
        })
    }
}