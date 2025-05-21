package com.example.proyecto.data.interfaces.notifications

import android.content.Context
import com.example.proyecto.model.notification.Notification

interface NotificationApiRep {
    fun getNotifications(context: Context,callback: (List<Notification>?)-> Unit)

}