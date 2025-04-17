package com.example.proyecto.utils

import org.json.JSONObject

object JwtUtils {
    fun isTokenExpired(token: String): Boolean {
        return try {
            val payload = token.split(".")[1]
            val json = String(
                android.util.Base64.decode(payload, android.util.Base64.URL_SAFE),
                Charsets.UTF_8
            )
            val expiration = JSONObject(json).getLong("exp") * 1000
            System.currentTimeMillis() > expiration
        } catch (e: Exception) {
            true // Si hay error, considerar como expirado
        }
    }
}
