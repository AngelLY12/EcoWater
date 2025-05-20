package com.example.proyecto.ui.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyecto.data.services.AlertApiService
import com.example.proyecto.model.alert.AlertRequest
import com.example.proyecto.data.services.AlertApiService.getAllAlerts
import com.example.proyecto.data.services.DeviceApiService
import com.example.proyecto.model.alert.UserAlertSettings
import com.example.proyecto.model.device.Device

class AlertsViewModel: ViewModel() {
    private val _alerts = mutableStateOf<List<UserAlertSettings>>(emptyList())
    val alerts: State<List<UserAlertSettings>> = _alerts
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun loadAlerts(context: Context) {
        _isLoading.value = true
        getAllAlerts(context) { alertsList ->
            _alerts.value = alertsList ?: emptyList()
            _isLoading.value = false
            Log.d("DeviceViewModel", "Alertas recibidos: ${_alerts.value}")
        }
    }

    fun updateDeviceInList(updatedAlert: UserAlertSettings) {
        _alerts.value = _alerts.value.map {
            if (it.id == updatedAlert.id) updatedAlert else it
        }
    }



    fun updateAlert(context: Context, alert: UserAlertSettings, onResult: ((Boolean) -> Unit)? = null) {
        AlertApiService.updateStatus(context = context, userAlertSettings = alert) { response ->
            if (response != null) {
                updateDeviceInList(alert)
                onResult?.invoke(true)
            } else {
                onResult?.invoke(false)
            }
        }
    }

}