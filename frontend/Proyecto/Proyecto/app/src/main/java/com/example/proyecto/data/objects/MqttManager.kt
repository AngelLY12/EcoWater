package com.example.proyecto.data.objects

import android.content.Context
import android.util.Log
import com.example.proyecto.data.services.SensorDataApiService
import com.example.proyecto.data.services.WaterLevelApiService
import com.example.proyecto.model.device.Device
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.json.JSONObject

object MqttManager {
    private var mqttClient: MqttClient? = null
    private const val BROKER_URL = "tcp://broker.emqx.io:1883"
    private const val TOPIC = "sensor/agua"

    fun connect(context: Context,
                onMessageReceived: (Float, Device) -> Unit,
                devices: List<Device>
    ) {
        Log.d("MQTT", "Lista: $devices")

        try {
            Log.d("MQTT", "Lista: $devices")

            val clientId = "AndroidClient_${System.currentTimeMillis()}"
            mqttClient = MqttClient(BROKER_URL, clientId,
                MemoryPersistence()

            ).apply {
                setCallback(object : MqttCallback {
                    override fun connectionLost(cause: Throwable?) {
                        cause?.printStackTrace()
                    }

                    override fun messageArrived(topic: String, message: MqttMessage) {
                        val json = JSONObject(String(message.payload))
                        try {
                            val distance = json.getDouble("distance").toFloat()
                            val deviceId = json.getString("deviceId")


                            val matchedDevice = devices.find { it.deviceId == deviceId }?.copy()

                            Log.d("MQTT_MATCH", "Dispositivo encontrado: $matchedDevice")

                            matchedDevice?.let { device ->
                                onMessageReceived(distance, device)
                            } ?: run {
                                Log.w("MQTT_WARNING", "Dispositivo $deviceId no encontrado")
                            }
                        } catch (e: NumberFormatException) {
                            e.printStackTrace()
                        }
                    }

                    override fun deliveryComplete(token: IMqttDeliveryToken?) {}
                })

                connect(MqttConnectOptions().apply {
                    isCleanSession = true
                })

                subscribe(TOPIC, 1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        try {
            mqttClient?.disconnect()
            mqttClient?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


