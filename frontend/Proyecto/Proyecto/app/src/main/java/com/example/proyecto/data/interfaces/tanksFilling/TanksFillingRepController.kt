package com.example.proyecto.data.interfaces.tanksFilling

import com.example.proyecto.model.tankFilling.TankFilling
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TanksFillingRepController {
    @GET("filling/getMainTankFillingsByDate")
    fun getMainTankFillingsByDate(@Query("date") date: String): Call<List<TankFilling>>

    @GET("filling/getMainTankFillingsByDateTime")
    fun getMainTankFillingsByDateTime(
        @Query("date") date: String,  // formato yyyy-MM-dd
        @Query("startHour") startHour: String, // formato HH:mm
        @Query("endHour") endHour: String // formato HH:mm
    ): Call<List<TankFilling>>

    @GET("filling/getMainTankFillingsByDateTime2")
    fun getMainTankFillingsByDateTime(
        @Query("startDate") startDate: String,  // Primer día del mes (yyyy-MM-dd)
        @Query("endDate") endDate: String,      // Último día del mes (yyyy-MM-dd)
        @Query("startHour") startHour: String,  // Hora inicial (HH:mm)
        @Query("endHour") endHour: String       // Hora final (HH:mm)
    ): Call<List<TankFilling>>
}