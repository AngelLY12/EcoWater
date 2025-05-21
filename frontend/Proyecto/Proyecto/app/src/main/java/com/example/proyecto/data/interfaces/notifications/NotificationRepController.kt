package com.example.proyecto.data.interfaces.notifications

import com.example.proyecto.model.level.Levels
import com.example.proyecto.model.notification.Notification
import retrofit2.Call
import retrofit2.http.GET

interface NotificationRepController {
    @GET("notifications/listAll")
    fun getNotifications(): Call<List<Notification>>
}