package com.example.proyecto.model.device

import com.example.proyecto.model.tank.Tank
import com.example.proyecto.model.tank.TankDTO
import com.example.proyecto.model.user.UserDTO

data class Device(
    val deviceId: String?=null,
    val user: UserDTO?=null,
    val deviceName: String?=null,
    val deviceType: DeviceType?=null,
    val deviceLocation: String?=null,
    val creationRegister: String?=null,
    val connected: Boolean?=null,
    val lastSeen: String?=null,
    val ssid: String?=null,
    val tank: Tank?=null
)
