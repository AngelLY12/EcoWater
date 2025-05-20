package com.example.proyecto.data.interfaces.users

import com.example.proyecto.model.auth.AuthRequest
import com.example.proyecto.model.auth.AuthResponse
import com.example.login.models.User
import com.example.proyecto.model.auth.GoogleAuthRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRepController {
    @POST("auth/register")
    fun registerUser(@Body user: User): Call<User>

    @POST("auth/login")
    fun loginUser(@Body token: AuthRequest): Call<AuthResponse>

    @POST("auth/google")
    fun loginWithGoogle(@Body request: GoogleAuthRequest): Call<AuthResponse>
}