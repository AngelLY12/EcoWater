package com.example.proyecto.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object GlobalEvent {
    private val _authEvents = MutableSharedFlow<AuthEvent>()
    val authEvents = _authEvents.asSharedFlow()

    sealed class AuthEvent {
        object Logout : AuthEvent()
    }

    suspend fun notifyLogout() {
        _authEvents.emit(AuthEvent.Logout)
    }
}
