package com.example.frontend.usecase

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.collection.emptyObjectList
import com.example.frontend.api.PlaceService
import com.example.frontend.data.defaultPlaceResponse
import com.example.frontend.data.defaultPlaces
import com.example.frontend.model.PlaceModel
import com.example.frontend.model.PlaceResponse
import com.example.frontend.model.PlaceResponseWrapper
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPlaceUseCase @RequiresApi(Build.VERSION_CODES.O) constructor(
    private val context: Context,
    private val friendsId: List<Long>,
    private val placeService: PlaceService = PlaceService.create(context)
) {
    fun fetch(onPlaceFetched: (List<PlaceResponse>) -> Unit) {
        val call = placeService.recommend(friendsId)

        call.enqueue(object : Callback<PlaceResponseWrapper> {

            override fun onResponse(
                call: Call<PlaceResponseWrapper>,
                response: Response<PlaceResponseWrapper>
            ) {
                if (response.isSuccessful) {
                    val places = response.body()!!
                    onPlaceFetched(places.places)

                } else {
                    onPlaceFetched(defaultPlaceResponse)
                }
            }
            override fun onFailure(call: Call<PlaceResponseWrapper>, t: Throwable) {
                onPlaceFetched(defaultPlaceResponse)
            }
        })
    }
}