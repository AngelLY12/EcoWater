package com.example.proyecto.ui.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.proyecto.data.services.DeviceApiService
import com.example.proyecto.data.services.DeviceApiService.getAllDevices
import com.example.proyecto.model.device.Device
import com.google.gson.Gson
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DeviceViewModel: ViewModel() {
    private val _devices = mutableStateOf<List<Device>>(emptyList())
    val devices: State<List<Device>> = _devices
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading


    suspend fun loadDevices(context: Context) {
        _isLoading.value = true
        getAllDevices(context) { deviceList ->
            _devices.value = deviceList ?: emptyList()
            _isLoading.value = false
            Log.d("DeviceViewModel", "Dispositivos recibidos: ${_devices.value}")
            if(deviceList?.isNotEmpty() == true){
                val sharedPref = context.getSharedPreferences("devices_pref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                val json = Gson().toJson(deviceList)
                editor.putString("devices", json)
                editor.apply()
            }

        }
    }

    suspend fun loadDevicesSync(context: Context): List<Device> = suspendCoroutine { cont ->
        _isLoading.value = true
        getAllDevices(context) { deviceList ->
            val list = deviceList ?: emptyList()
            _devices.value = list
            _isLoading.value = false

            Log.d("DeviceViewModel", "Dispositivos recibidos (sync): $list")

            cont.resume(list)
        }
    }


    fun updateDeviceInList(updatedDevice: Device) {
        _devices.value = _devices.value.map {
            if (it.deviceId == updatedDevice.deviceId) updatedDevice else it
        }
    }

    fun deleteDevice(deviceId: String, context: Context, onResult: ((Boolean) -> Unit)?=null) {
        _isLoading.value = true

        DeviceApiService.deleteDevice(deviceId, context) { response ->
            _isLoading.value = false
            if (response != null && response != "Failed") {
                _devices.value = _devices.value.filter { it.deviceId != deviceId }
                onResult?.invoke(true)
            } else {
                onResult?.invoke(false)

            }
        }
    }

    fun updateDevice(context: Context, device: Device, onResult: ((Boolean) -> Unit)? = null) {
        _isLoading.value = true
        DeviceApiService.updateDevice(context = context, device = device) { response ->
            _isLoading.value = false
            if (response != null) {
                updateDeviceInList(device)
                onResult?.invoke(true)
            } else {
                onResult?.invoke(false)
            }
        }
    }

}