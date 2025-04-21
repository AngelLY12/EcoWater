package com.example.proyecto.data.interfaces.sensorDatas

import android.content.Context
import com.example.proyecto.model.sensor.SensorDataRequest

interface SensorDataApiRep {
    fun addData(sensorData: SensorDataRequest,context: Context, callback: (SensorDataRequest?)->Unit)
}