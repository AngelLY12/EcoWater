package com.example.proyecto.model.models

import java.math.BigDecimal

data class Tank(
    val tankId: Long?=null,
    val tankName: String?=null,
    val capacity: Float?=null,
    val fillingType: String?=null,
    val tankHeight: Float?=null,
    val isMain: Boolean?=null
)

