package com.example.proyecto.model.models

import java.sql.Timestamp

data class LevelResponse(
    val tank: TankDTO,
    val waterLevel: Float,
    val fillPercentage: Float
)
