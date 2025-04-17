package com.example.proyecto.data.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.login.objects.RetrofitInstance
import com.example.proyecto.data.interfaces.devices.DeviceApiRep
import com.example.proyecto.model.models.Device
import com.example.proyecto.model.models.DeviceRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DeviceApiService: DeviceApiRep {
    override fun create(
        device: DeviceRequest,
        context: Context,
        callback: (DeviceRequest?)-> Unit
    ) {
        RetrofitInstance.getDevice(context).saveDevice(device).enqueue(object : Callback<DeviceRequest>{
            override fun onResponse(
                call: Call<DeviceRequest?>,
                response: Response<DeviceRequest?>
            ) {
                if (response.isSuccessful) {
                    val deviceResponse=response.body()
                    callback(deviceResponse)
                    Toast.makeText(context, "Dipositivo registrado", Toast.LENGTH_SHORT).show()
                } else {
                    callback(null)
                    Toast.makeText(context, "Error al registrar el dispositivo", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<DeviceRequest?>,
                t: Throwable
            ) {
                callback(null)
                Toast.makeText(context, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Error de conexión", t)
            }

        })
    }

    override fun getAllDevices(
        context: Context,
        callback: (List<Device>?) -> Unit
    ) {
        RetrofitInstance.getDevice(context).getDevices().enqueue(object : Callback<List<Device>>{
            override fun onResponse(
                call: Call<List<Device>?>,
                response: Response<List<Device>?>
            ) {
                if (response.isSuccessful) {
                    val deviceList = response.body()
                    callback(deviceList)
                } else {
                    callback(null)
                    Toast.makeText(context, "Error al obtener los dispositivos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<List<Device>?>,
                t: Throwable
            ) {
                callback(null)
                Toast.makeText(context, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Error de conexión", t)            }

        })
    }
}