package com.example.proyecto.model.notification

import java.time.LocalDateTime

data class Notification(
    val idNotification: Long,
    val title: String,
    val message: String,
    val createdAt: String,
    val alertType: String
)
