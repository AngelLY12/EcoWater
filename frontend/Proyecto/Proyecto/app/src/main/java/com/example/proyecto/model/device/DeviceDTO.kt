package com.example.proyecto.model.device

import com.example.proyecto.model.tank.TankDTO
import com.example.proyecto.model.user.UserDTO

data class DeviceDTO(
    val deviceId: String,
    val user: UserDTO,
    val tank: TankDTO
)
