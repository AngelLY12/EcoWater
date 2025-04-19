package com.example.proyecto.model.models

import java.sql.Timestamp

data class Levels(
    val levelId: Long?=null,
    val tank: TankDTO?=null,
    val waterLevel: Float?=null,
    val dateMeasurement: Timestamp?=null
)
