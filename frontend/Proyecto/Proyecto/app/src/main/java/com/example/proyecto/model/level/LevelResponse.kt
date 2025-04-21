package com.example.proyecto.model.level

import com.example.proyecto.model.tank.TankDTO

data class LevelResponse(
    val tank: TankDTO,
    val waterLevel: Float,
    val fillPercentage: Float
)
