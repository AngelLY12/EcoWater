package com.example.proyecto.model.alert

import com.example.proyecto.model.user.UserDTO

data class AlertRequest(
    var alertType: String,
    var enabled: Boolean,
    var threshold: Float,
    var unit: String,
    var title: String,
    var message: String
    )
