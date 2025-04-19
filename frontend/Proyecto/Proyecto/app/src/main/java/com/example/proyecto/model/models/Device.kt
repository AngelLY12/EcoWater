package com.example.proyecto.model.models

import java.security.Timestamp

data class Device(
    val deviceId: String,
    val user: UserDTO,
    val deviceName: String,
    val deviceType: DeviceType,
    val deviceLocation: String,
    val creationRegister: String,
    val connected: Boolean,
    val lastSeen: String,
    val ssid: String
)
