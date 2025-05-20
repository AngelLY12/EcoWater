package com.example.proyecto.model.level

import com.example.proyecto.model.tank.TankDTO
import java.sql.Timestamp

data class Levels(
    val levelId: Long?=null,
    val tank: TankDTO?=null,
    val waterLevel: Float?=null,
    val dateMeasurement: Timestamp?=null,
    val fillPercentage: Float?=null
)
