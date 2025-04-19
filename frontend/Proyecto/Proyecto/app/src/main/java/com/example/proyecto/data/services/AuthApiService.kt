package com.example.proyecto.data.services

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.example.login.models.AuthRequest
import com.example.login.models.AuthResponse
import com.example.login.objects.RetrofitInstance
import com.example.proyecto.data.interfaces.users.ApiAuthService
import com.example.proyecto.ui.components.CustomToast
import com.example.proyecto.ui.components.ToastType
import com.example.proyecto.utils.JwtUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

object AuthApiService: ApiAuthService {
    override fun authUser(authRequest: AuthRequest, context: Context,callback: (AuthResponse?) -> Unit) {
        RetrofitInstance.getAuthApi().loginUser(authRequest).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    val token = authResponse?.token
                    saveToken(context, token)
                    callback(authResponse)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                callback(null)
                Log.e("AUTH_ERROR", "Fallo de conexión: ${t.message}", t)
            }

        })
    }
    fun saveToken(context: Context, token: String?) {
        val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit() {
            //putString("TOKEN_KEY", token)
            putString("token", token)
        }
    }

    fun getToken(context: Context): String? {
        val sharedPrefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val token = sharedPrefs.getString("token", null)
        Log.d("TOKEN_MANAGER", "Obteniendo token: $token")
        return if (token != null && JwtUtils.isTokenExpired(token)) {
            Log.d("TOKEN_MANAGER", "Token expirado, limpiando.")
            saveToken(context, null) // Auto-limpieza
            null
        } else {
            token
        }
    }
    /*
    val sharedPrefs = EncryptedSharedPreferences.create(
        "SecurePrefs",
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
     */

}