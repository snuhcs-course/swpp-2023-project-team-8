package com.example.frontend.api
import com.example.frontend.model.PlaceModel
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface PlaceService {
    @POST("/sendlocation")
    fun send(@Body placeModel: PlaceModel?): Call<PlaceModel>?

    @GET("/recommend")
    fun recommend(@Query("averagedLocation") averagedLocation: LatLng?): Call<List<PlaceModel>>


}
