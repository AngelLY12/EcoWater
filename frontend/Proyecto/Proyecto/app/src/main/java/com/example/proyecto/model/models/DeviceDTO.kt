package com.example.proyecto.model.models

data class DeviceDTO(
    val deviceId: String,
    val user: UserDTO,
    val tank: TankDTO
)
