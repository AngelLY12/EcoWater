package com.example.proyecto.model.sensor

import com.example.proyecto.model.device.DeviceDTO
import com.google.firebase.Timestamp

data class SensorDataResponse(
    val sensorId: Long,
    val device: DeviceDTO,
    val distance:Float,
    val measurementTime: String
)
