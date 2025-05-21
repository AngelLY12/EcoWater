package com.example.proyecto.model.level

import com.example.proyecto.model.tank.TankDTO
import java.sql.Timestamp

data class LevelResponse(
    val tank: TankDTO,
    val waterLevel: Float,
    val fillPercentage: Float,
    val dateMeasurement: Timestamp?=null
)
