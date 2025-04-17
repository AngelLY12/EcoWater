package com.example.proyecto.data.interfaces.devices

import android.content.Context
import com.example.proyecto.model.models.Device
import com.example.proyecto.model.models.DeviceRequest
import com.example.proyecto.model.models.LevelResponse
import com.example.proyecto.model.models.Tank

interface DeviceApiRep {
    fun create(device: DeviceRequest, context: Context, callback: (DeviceRequest?)-> Unit)
    fun getAllDevices(context: Context, callback: (List<Device>?) -> Unit)

}