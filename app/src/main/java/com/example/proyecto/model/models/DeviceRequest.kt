package com.example.proyecto.model.models

data class DeviceRequest(
    val device_id: String?=null,
    val device_name: String?=null,
    val device_type: String?=null,
    val device_location: String?=null,
    val ssid:String?=null,
    val tank: Tank?=null
)