package com.example.frontend.usecase

import android.content.Context
import android.util.Log
import com.example.frontend.api.PlaceService
import com.example.frontend.data.defaultPlaces
import com.example.frontend.model.PlaceModel
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPlaceUseCase(
    private val context: Context,
    private val friendsId: List<Long>,
    private val placeService: PlaceService = PlaceService.create()
) {
    fun fetch(onPlaceFetched: (List<PlaceModel>) -> Unit) {
        val call = placeService.recommend(friendsId)
        call.enqueue(object : Callback<List<PlaceModel>> {
            override fun onResponse(
                call: Call<List<PlaceModel>>,
                response: Response<List<PlaceModel>>
            ) {
                if (response.isSuccessful) {
                    val places = response.body() ?: emptyList()
                    onPlaceFetched(places)
                } else {
                    onPlaceFetched(defaultPlaces)
                }
            }
            override fun onFailure(call: Call<List<PlaceModel>>, t: Throwable) {
                onPlaceFetched(defaultPlaces)
                Log.d("Place", defaultPlaces.toString())
            }
        })
    }
}