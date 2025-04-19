package com.example.proyecto.data.interfaces.users

import android.content.Context
import com.example.login.models.AuthRequest
import com.example.login.models.AuthResponse

interface ApiAuthService {
    fun authUser(authRequest: AuthRequest, context: Context, callback: (AuthResponse?) -> Unit)
}