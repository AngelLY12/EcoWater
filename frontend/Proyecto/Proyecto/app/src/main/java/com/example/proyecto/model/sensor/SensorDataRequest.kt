package com.example.proyecto.model.sensor

import com.example.proyecto.model.device.DeviceDTO

data class SensorDataRequest(
    val device: DeviceDTO,
    val distance: Float
)