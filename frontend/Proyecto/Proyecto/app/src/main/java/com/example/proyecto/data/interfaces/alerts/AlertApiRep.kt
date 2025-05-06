package com.example.proyecto.data.interfaces.alerts

import android.content.Context
import com.example.proyecto.model.alert.AlertRequest
import com.example.proyecto.model.alert.UserAlertSettings
import com.example.proyecto.model.device.Device
import com.example.proyecto.model.device.DeviceRequest

interface AlertApiRep {
    fun create(userAlertSettings: AlertRequest, context: Context, callback: (AlertRequest?)-> Unit)
    fun updateStatus(userAlertSettings: UserAlertSettings, context: Context, callback: (Boolean?)-> Unit)
    fun getAllAlerts(context: Context, callback: (List<UserAlertSettings>?) -> Unit)

}