package com.example.proyecto

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.proyecto.data.objects.MqttManager
import com.example.proyecto.data.services.MyFirebaseMessagingService
import com.example.proyecto.model.device.Device
import com.example.proyecto.ui.viewModels.DeviceViewModel
import com.example.proyecto.ui.viewModels.SensorDataHelper
import com.google.firebase.FirebaseApp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyApp : Application() {
    lateinit var deviceViewModel: DeviceViewModel
    private var mqttRefreshJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        MyFirebaseMessagingService.initialize(this)
        MyFirebaseMessagingService.retryPendingToken()
        val authPrefs = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val token = authPrefs.getString("token", null)
        deviceViewModel = DeviceViewModel()
        Log.d("MQTT", "Token de MyApp: $token")

        if (token != null) {
            setupMqttWithRefresh()
        }
    }
    private fun setupMqttWithRefresh() {
        mqttRefreshJob?.cancel()
        mqttRefreshJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                try {
                    val devices = deviceViewModel.loadDevicesSync(this@MyApp)
                    devices?.let { freshDevices ->
                        MqttManager.connect(
                            context = this@MyApp,
                            onMessageReceived = { distance, device ->
                                if (device.tank != null) {
                                    SensorDataHelper.sendSensorData(distance, device, this@MyApp)
                                } else {
                                    Log.w("MQTT_WARNING", "Dispositivo ${device.deviceId} no tiene tanque asociado")
                                }
                            },
                            devices = freshDevices
                        )
                        Log.d("MQTT_REFRESH", "Dispositivos actualizados para MQTT")
                    }
                    delay(300_000)
                } catch (e: Exception) {
                    Log.e("MQTT_REFRESH", "Error actualizando dispositivos", e)
                    delay(60_000)
                }
            }
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        mqttRefreshJob?.cancel()
        MqttManager.disconnect()
    }

}

/*

        if (token != null) {
            try {

                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        val devices = deviceViewModel.loadDevicesSync(this@MyApp)
                        if(devices!=null){

                            MqttManager.connect(this@MyApp, { distance, device ->

                                if (device.tank != null) {
                                    SensorDataHelper.sendSensorData(distance, device, this@MyApp)
                                } else {
                                    Log.w("MQTT_WARNING", "Dispositivo ${device.deviceId} no tiene tanque asociado")
                                }                            }, devices)
                        }
                    } catch (e: Exception) {
                        Log.e("MyAPP", "Error al cargar devices: ${e.message}")
                    }
                }



            }catch (e: Exception){
                Log.e("MyAPP", "Error al deserializar devices: ${e.message}")
            }

        }



    }
 */
