package com.example.frontend.usecase

import android.content.Context
import android.widget.Toast
import com.example.frontend.api.MissionService
import com.example.frontend.data.defaultMissions

import com.example.frontend.model.MissionModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMissionUseCase(
    private val context: Context,
    private val missionService: MissionService = MissionService.create(context)
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
                    var result = "List Mission failed: " + response.errorBody()?.string()
                    Toast.makeText(context, result, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<MissionModel>>, t: Throwable) {
                var result = "List Mission error: " + t.message
                Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            }
        })
    }
}
