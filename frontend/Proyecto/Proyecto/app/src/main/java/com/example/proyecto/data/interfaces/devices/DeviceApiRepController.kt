package com.example.proyecto.data.interfaces.devices

import com.example.proyecto.model.models.Device
import com.example.proyecto.model.models.DeviceRequest
import com.example.proyecto.model.models.Tank
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DeviceApiRepController {
    @POST("device/addDevice")
    fun saveDevice(@Body deviceData: DeviceRequest): Call<DeviceRequest>

    @GET("device/getAllDevices")
    fun getDevices(): Call<List<Device>>
}