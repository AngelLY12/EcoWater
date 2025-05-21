package com.example.proyecto.data.interfaces.levels

import com.example.proyecto.model.level.LevelRequest
import retrofit2.Call
import com.example.proyecto.model.level.LevelResponse
import com.example.proyecto.model.level.Levels
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Date

interface WaterLevelRepController {

    @GET("level/getLevel")
    fun getTopLevel(): Call<LevelResponse>

    @GET("level/getLevels")
    fun getLevels(): Call<List<Levels>>

    @POST("level/addLevel")
    fun addLevel(@Body waterTankLevel: LevelRequest): Call<Levels>

    @GET("level/getLevelsMain")
    fun getLevelsMain(): Call<List<Levels>>

    @GET("level/getLevelsMainByDate")
    fun getLevelsMainByDate(@Query("date") date: String): Call<List<Levels>>

    @GET("level/getFirstMeasurementDate")
    fun getFirstMeasurementLevel(): Call<LevelResponse> // o WaterTankLevel si tienes ese nombre exacto

    @GET("level/getLevelsMainByDateTime")
    fun getLevelsMainByDateTime(
        @Query("date") date: String,  // formato yyyy-MM-dd
        @Query("startHour") startHour: String, // formato HH:mm
        @Query("endHour") endHour: String // formato HH:mm
    ): Call<List<Levels>>

    @GET("level/getLevelsMainByDateTime2")
    fun getLevelsMainByDateTime(
        @Query("startDate") startDate: String,  // Primer día del mes (yyyy-MM-dd)
        @Query("endDate") endDate: String,      // Último día del mes (yyyy-MM-dd)
        @Query("startHour") startHour: String,  // Hora inicial (HH:mm)
        @Query("endHour") endHour: String       // Hora final (HH:mm)
    ): Call<List<Levels>>

    @GET("level/getAvailableDates")
    fun getAvailableDatesMain(): Call<List<Date>> // Retrofit devolverá la fecha como String

    @GET("level/getLevelsMainByMonth")
    fun getLevelsMainByMonth(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Call<Map<String, List<Levels>>>
}