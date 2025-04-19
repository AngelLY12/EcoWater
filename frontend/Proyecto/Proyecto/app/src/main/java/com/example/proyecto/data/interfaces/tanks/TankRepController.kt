package com.example.proyecto.data.interfaces.tanks

import com.example.proyecto.model.models.Tank
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface TankRepController {
    @POST("tank/addTank")
    fun createTank(@Body tank: Tank): Call<Tank>

    @GET("tank/getAllTanks")
    fun getTanks(): Call<List<Tank>>

    @GET("tank/getTank/{tankId}")
    fun getTank(@Body tankId: Long): Call<Tank>

    @PATCH("tank/updateTank")
    fun updateTank(@Body tank: Tank): Call<Tank>
}