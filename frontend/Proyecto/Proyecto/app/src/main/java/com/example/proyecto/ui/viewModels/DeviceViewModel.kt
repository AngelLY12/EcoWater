package com.example.proyecto.ui.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyecto.data.services.DeviceApiService
import com.example.proyecto.data.services.DeviceApiService.getAllDevices
import com.example.proyecto.data.services.TankApiService
import com.example.proyecto.model.models.Device
import com.example.proyecto.model.models.DeviceRequest
import com.example.proyecto.model.models.Tank

class DeviceViewModel: ViewModel() {
    private val _devices = mutableStateOf<List<Device>>(emptyList())
    val devices: State<List<Device>> = _devices
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun loadDevices(context: Context) {
        _isLoading.value = true
        getAllDevices(context) { deviceList ->
            _devices.value = deviceList ?: emptyList()
            _isLoading.value = false
            Log.d("DeviceViewModel", "Dispositivos recibidos: ${_devices.value}")
        }
    }

    fun updateDeviceInList(updatedDevice: Device) {
        _devices.value = _devices.value.map {
            if (it.deviceId == updatedDevice.deviceId) updatedDevice else it
        }
    }

    fun deleteDevice(deviceId: String, context: Context) {
        _isLoading.value = true

        DeviceApiService.deleteDevice(deviceId, context) { response ->
            _isLoading.value = false
            if (response != null && response != "Failed") {
                _devices.value = _devices.value.filter { it.deviceId != deviceId }
                Toast.makeText(context, "Dispositivo eliminado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun updateDevice(context: Context, device: Device) {
        _isLoading.value = true
        DeviceApiService.updateDevice(context = context, device = device) { response ->
            _isLoading.value = false
            if (response != null) {
                updateDeviceInList(device)
                Toast.makeText(context, "Campo actualizado correctamente", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
            }
        }
    }

}