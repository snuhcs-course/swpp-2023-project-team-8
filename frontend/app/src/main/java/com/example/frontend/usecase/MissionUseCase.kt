package com.example.frontend.usecase

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.frontend.api.AuthService
import com.example.frontend.api.MissionService
import com.example.frontend.defaultMissions
import com.example.frontend.model.MissionModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissionUseCase(
    private val context: Context,
    private val missionService: MissionService = MissionService.create()
) {
        fun fetch(): List<MissionModel> {
        var missions by mutableStateOf<List<MissionModel>>(emptyList())
        val call = missionService.getMissionList()

        call.enqueue(object : Callback<List<MissionModel>> {
            override fun onResponse(
                call: Call<List<MissionModel>>,
                response: Response<List<MissionModel>>
            ) {
                if (response.isSuccessful) {
                    missions = response.body() ?: emptyList()

                } else {

                }
            }

            override fun onFailure(call: Call<List<MissionModel>>, t: Throwable) {
                    missions = defaultMissions
            }
        })
            return missions
    }
}