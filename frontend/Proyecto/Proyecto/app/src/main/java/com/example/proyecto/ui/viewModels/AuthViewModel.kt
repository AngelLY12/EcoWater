package com.example.proyecto.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto.data.services.AuthApiService.getToken
import com.example.proyecto.data.services.AuthApiService.saveToken
import com.example.proyecto.utils.GlobalEvent
import com.example.proyecto.utils.JwtUtils.isTokenExpired
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun updateLoginState(context: Context) {
        val token = getToken(context)

        if (token != null && isTokenExpired(token)) {
            logout(context)
        } else {
            _isLoggedIn.value = token != null
        }
    }
    fun logout(context: Context) {
        // Eliminar el token
        saveToken(context, null)
        _isLoggedIn.value = false

        viewModelScope.launch {
            GlobalEvent.notifyLogout()
        }
    }

}