package com.example.frontend.usecase

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.frontend.api.PlaceService
import com.example.frontend.model.PlaceResponse
import com.example.frontend.model.PlaceResponseWrapper
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
                    var result = "List Place failed: " + response.errorBody()?.string()
                    Toast.makeText(context, result, Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<PlaceResponseWrapper>, t: Throwable) {
                var result = "List Place failed: " + t.message
                Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            }
        })
    }
}