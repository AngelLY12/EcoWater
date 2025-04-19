package com.example.proyecto.model.models

import java.security.Timestamp

data class Device(
    val deviceId: String?=null,
    val user: UserDTO?=null,
    val deviceName: String?=null,
    val deviceType: DeviceType,
    val deviceLocation: String?=null,
    val creationRegister: String?=null,
    val connected: Boolean?=null,
    val lastSeen: String?=null,
    val ssid: String?=null
)
