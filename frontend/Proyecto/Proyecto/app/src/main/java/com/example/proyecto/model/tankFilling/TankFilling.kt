package com.example.proyecto.model.tankFilling

import com.example.proyecto.model.tank.TankDTO
import java.sql.Timestamp

data class TankFilling (
    val tankFillingId: Long? = null,
    val tank: TankDTO? = null,
    val startedDate: Timestamp? = null,
    val finishedDate: Timestamp? = null,
    val totalVolume: Float? = null
)