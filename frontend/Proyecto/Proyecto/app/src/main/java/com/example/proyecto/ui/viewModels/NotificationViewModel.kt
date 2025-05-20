package com.example.proyecto.ui.viewModels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyecto.data.services.NotificationApiService.getNotifications
import com.example.proyecto.model.device.Device
import com.example.proyecto.model.notification.Notification

class NotificationViewModel: ViewModel() {
    private val _notifications = mutableStateOf<List<Notification>>(emptyList())
    val notifications: State<List<Notification>> = _notifications
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun loadNotifications(context: Context) {
        _isLoading.value = true
        getNotifications(context) { notiList ->
            _notifications.value = notiList ?: emptyList()
            _isLoading.value = false
            Log.d("NotificationViewModel", "Notificaciones recibidos: ${_notifications.value}")
        }
    }

}