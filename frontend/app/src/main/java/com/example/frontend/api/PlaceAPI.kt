package com.example.frontend.api

import com.example.frontend.model.AuthResponse
import com.example.frontend.model.EmailModel
import com.example.frontend.model.LoginModel
import com.example.frontend.model.PlaceModel
import com.example.frontend.model.RegisterModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PlaceAPI {
    @POST("/sendlocation")
    fun send(@Body placeModel: PlaceModel?): Call<PlaceModel>?

    //@GET("/recommend")
   // fun recommend():
}
