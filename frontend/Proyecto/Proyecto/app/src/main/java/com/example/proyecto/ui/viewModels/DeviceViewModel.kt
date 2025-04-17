package com.example.proyecto.ui.viewModels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyecto.data.services.DeviceApiService.getAllDevices
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
}