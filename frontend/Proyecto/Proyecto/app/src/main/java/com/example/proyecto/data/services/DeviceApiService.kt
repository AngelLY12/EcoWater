package com.example.proyecto.data.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.login.objects.RetrofitInstance
import com.example.proyecto.data.interfaces.devices.DeviceApiRep
import com.example.proyecto.model.device.Device
import com.example.proyecto.model.device.DeviceRequest
import okhttp3.ResponseBody
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
                } else {
                    callback(null)
                }
            }

            override fun onFailure(
                call: Call<DeviceRequest?>,
                t: Throwable
            ) {
                callback(null)
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
                }
            }

            override fun onFailure(
                call: Call<List<Device>?>,
                t: Throwable
            ) {
                callback(null)
                Log.e("API_ERROR", "Error de conexión", t)            }

        })
    }

    override fun updateDevice(
        device: Device,
        context: Context,
        callback: (Device?) -> Unit
    ) {
        RetrofitInstance.getDevice(context).updateDevice(device).enqueue(object : Callback<Device>{
            override fun onResponse(
                call: Call<Device?>,
                response: Response<Device?>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(
                call: Call<Device?>,
                t: Throwable
            ) {
                callback(null)
                Log.e("API_ERROR", "Error de conexión", t)            }

        })
    }

    override fun deleteDevice(
        deviceId: String,
        context: Context,
        callback: (String?) -> Unit
    ) {
        RetrofitInstance.getDevice(context).deleteDevice(deviceId).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if(response.isSuccessful){
                    callback("Dipositivo eliminado correctamente")
                }else{
                    callback("Failed")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback(null)
                Log.e("API_ERROR", "Error de conexión", t)                      }

        })    }
}