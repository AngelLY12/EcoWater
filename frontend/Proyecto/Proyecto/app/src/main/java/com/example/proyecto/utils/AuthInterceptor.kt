package com.example.proyecto.utils

import android.content.Context
import android.util.Log
import com.example.proyecto.data.services.AuthApiService.getToken
import com.example.proyecto.data.services.AuthApiService.saveToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val context: Context,
                       ) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response { // No suspend aquí
        val token = getToken(context)
        val builder = chain.request().newBuilder()

        if (!token.isNullOrEmpty()) {
            builder.addHeader("Authorization", "Bearer $token")
        }

        val request = builder.build()

        val response = chain.proceed(request)
        Log.e("AUTH_ERROR", "El token es: ${token}, La reques es: ${request}, y la response es: ${response}")
        if (response.code() == 401) {
            CoroutineScope(Dispatchers.IO).launch {
                cleanupAuthState(context)
            }
        }
        if (response.code() == 403) {
            CoroutineScope(Dispatchers.IO).launch {
                cleanupAuthState(context)
            }
        }
        return response
    }

    private suspend fun cleanupAuthState(context: Context) { // Mantiene suspend
        withContext(Dispatchers.IO) {
            saveToken(context, null)
            GlobalEvent.notifyLogout()
        }
    }
}

