package com.example.proyecto.data.interfaces.sensorDatas

import com.example.proyecto.model.models.Levels
import com.example.proyecto.model.models.SensorDataRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SensorDataRepController {
    @POST("sensor/addSensorData")
    fun addData(@Body sensorData: SensorDataRequest): Call<SensorDataRequest>

}