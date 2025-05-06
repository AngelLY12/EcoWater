package com.example.proyecto.data.interfaces.alerts

import com.example.proyecto.model.alert.AlertRequest
import com.example.proyecto.model.alert.UserAlertSettings
import com.example.proyecto.model.device.Device
import com.example.proyecto.model.device.DeviceRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AlertRepController {
    @POST("alerts/addAlert")
    fun saveAlert(@Body userAlertSettings: AlertRequest): Call<AlertRequest>
    @PATCH("alerts/enable-status")
    fun updateStatus(@Body userAlertSettings: UserAlertSettings): Call<Boolean>
    @GET("alerts/getAllAlerts")
    fun getAlerts(): Call<List<UserAlertSettings>>
}