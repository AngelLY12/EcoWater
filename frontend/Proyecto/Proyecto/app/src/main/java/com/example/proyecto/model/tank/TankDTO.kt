package com.example.proyecto.model.tank

import com.example.proyecto.model.user.UserDTO


data class TankDTO(
    val tankId: Long?=null,
    val tankName: String?=null,
    val capacity: Float?=null,
    val tankHeight: Float?=null,
    val user: UserDTO?=null,
    val isMain: Boolean?=null
)
