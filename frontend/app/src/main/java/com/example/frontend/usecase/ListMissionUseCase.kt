package com.example.frontend.usecase

import android.content.Context
import com.example.frontend.api.MissionService
import com.example.frontend.data.defaultMissions

import com.example.frontend.model.MissionModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMissionUseCase(
    private val context: Context,
    private val missionService: MissionService = MissionService.create()
) {
    fun fetch(onMissionsFetched: (List<MissionModel>) -> Unit) {
        val call = missionService.getMissionList()

        call.enqueue(object : Callback<List<MissionModel>> {
            override fun onResponse(
                call: Call<List<MissionModel>>,
                response: Response<List<MissionModel>>
            ) {
                if (response.isSuccessful) {
                    val missions = response.body() ?: emptyList()
                    onMissionsFetched(missions)
                } else {
                    onMissionsFetched(defaultMissions)
                }
            }

            override fun onFailure(call: Call<List<MissionModel>>, t: Throwable) {
                onMissionsFetched(defaultMissions)
            }
        })
    }
}
