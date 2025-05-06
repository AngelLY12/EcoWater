package com.example.proyecto.model.alert

import com.example.login.models.User
import com.example.proyecto.model.user.UserDTO
import java.time.LocalDateTime

data class UserAlertSettings(
    val id: Long,
    val alertType: String,
    var enabled: Boolean,
    val threshold: Float,
    val unit: String,
    val createdAt: String,
    val title: String,
    val message: String
)
