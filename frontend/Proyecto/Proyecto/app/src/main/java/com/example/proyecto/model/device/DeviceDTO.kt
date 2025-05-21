package com.example.proyecto.model.device

import com.example.proyecto.model.tank.TankDTO
import com.example.proyecto.model.user.UserDTO

data class DeviceDTO(
    val deviceId: String?=null,
    val user: UserDTO?=null,
    val tank: TankDTO?=null
)
