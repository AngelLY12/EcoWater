package com.example.proyecto.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto.ui.components.ToastType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class ToastViewModel : ViewModel() {
    var toastMessage by mutableStateOf("")
        private set

    var toastVisible by mutableStateOf(false)
        private set

    var toastType by mutableStateOf(ToastType.SUCCESS)
        private set

    fun showToast(message: String, type: ToastType = ToastType.SUCCESS) {
        toastMessage = message
        toastType = type
        toastVisible = true

        viewModelScope.launch {
            delay(3000)
            toastVisible = false
        }
    }

    fun dismissToast() {
        toastVisible = false
    }
}

