package com.example.proyecto.ui.viewModels

import android.content.Context
import android.util.Log
import com.example.proyecto.data.services.SensorDataApiService
import com.example.proyecto.data.services.WaterLevelApiService
import com.example.proyecto.model.device.Device
import com.example.proyecto.model.device.DeviceDTO
import com.example.proyecto.model.level.LevelRequest
import com.example.proyecto.model.sensor.SensorDataRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object SensorDataHelper {
    fun sendSensorData(distance: Float, device: Device, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(10_000)
            val request = SensorDataRequest(distance = distance, device = device)
            SensorDataApiService.addData(request, context) { response ->
                if (response != null) {
                    sendWaterLevelData(device, context)
                } else {
                    Log.e("API_ERROR", "Fallo al enviar dato de sensor")
                }
            }
        }
    }

    private fun sendWaterLevelData(device: Device, context: Context) {
        val levelRequest = LevelRequest(tank = device.tank)
        WaterLevelApiService.addLevel(levelRequest, context) { response ->
            if (response != null) {
                Log.d("API_RESPONSE", "Dato de tanque enviado correctamente: $response")
            } else {
                Log.d("MQTT", "Tanque del dispositivo: ${device.tank}")
                Log.e("API_ERROR", "Fallo al enviar dato de tanque")
            }
        }
    }
}
