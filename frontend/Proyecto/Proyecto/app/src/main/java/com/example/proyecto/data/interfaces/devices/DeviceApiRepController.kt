package com.example.proyecto.data.interfaces.devices

import com.example.proyecto.model.device.Device
import com.example.proyecto.model.device.DeviceRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface DeviceApiRepController {
    @POST("device/addDevice")
    fun saveDevice(@Body deviceData: DeviceRequest): Call<DeviceRequest>

    @GET("device/getAllDevices")
    fun getDevices(): Call<List<Device>>

    @PATCH("device/updateDevice")
    fun updateDevice(@Body device: Device): Call<Device>

    @DELETE("device/deleteDevice/{deviceId}")
    fun deleteDevice(@Path("deviceId") deviceId: String): Call<ResponseBody>
}