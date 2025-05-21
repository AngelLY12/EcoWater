package com.example.proyecto.data.interfaces.users

import android.content.Context
import com.example.proyecto.model.auth.AuthRequest
import com.example.proyecto.model.auth.AuthResponse
import com.example.proyecto.model.auth.GoogleAuthRequest

interface ApiAuthService {
    fun authUser(authRequest: AuthRequest, context: Context, callback: (AuthResponse?) -> Unit)
    fun authGoogle(request: GoogleAuthRequest, context: Context, callback: (AuthResponse?) -> Unit)
}