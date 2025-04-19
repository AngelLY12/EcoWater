package com.example.proyecto.data.interfaces.tanks

import com.example.proyecto.model.models.Tank
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TankRepController {
    @POST("tank/addTank")
    fun createTank(@Body tank: Tank): Call<Tank>

    @GET("tank/getAllTanks")
    fun getTanks(): Call<List<Tank>>

    @PATCH("tank/updateTank")
    fun updateTank(@Body tank: Tank): Call<Tank>

    @DELETE("tank/deleteTank/{tankId}")
    fun deleteTank(@Path("tankId") tankId: Long): Call<ResponseBody>
}