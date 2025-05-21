package com.example.proyecto.data.interfaces.sensorDatas

import com.example.proyecto.model.sensor.SensorDataRequest
import com.example.proyecto.model.sensor.SensorDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SensorDataRepController {
    @POST("sensor/addSensorData")
    fun addData(@Body sensorData: SensorDataRequest): Call<SensorDataRequest>

}