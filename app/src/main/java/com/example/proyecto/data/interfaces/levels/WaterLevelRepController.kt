package com.example.proyecto.data.interfaces.levels

import retrofit2.Call
import com.example.proyecto.model.models.LevelResponse
import com.example.proyecto.model.models.Levels
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WaterLevelRepController {

    @GET("level/getLevel")
    fun getTopLevel(): Call<LevelResponse>

    @GET("level/getLevels")
    fun getLevels(): Call<List<Levels>>

    @POST
    fun addLevel(@Body levels: Levels): Call<Levels>
}