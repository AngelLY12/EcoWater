package com.example.proyecto.ui.viewModels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto.data.services.AuthApiService
import com.example.proyecto.data.services.AuthApiService.getToken
import com.example.proyecto.data.services.AuthApiService.saveToken
import com.example.proyecto.model.auth.GoogleAuthRequest
import com.example.proyecto.utils.GlobalEvent
import com.example.proyecto.utils.JwtUtils.isTokenExpired
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn
    var isLoading by mutableStateOf(false)
        private set
    fun updateLoginState(context: Context) {
        val token = getToken(context)

        if (token != null && isTokenExpired(token)) {
            logout(context)
        } else {
            _isLoggedIn.value = token != null
        }
    }
    fun logout(context: Context) {
        saveToken(context, null)
        _isLoggedIn.value = false

        viewModelScope.launch {
            GlobalEvent.notifyLogout()
        }
    }
    fun logoutSilently(context: Context) {
        saveToken(context, null)
        _isLoggedIn.value = false
    }

    fun onGoogleAuthResult(result: androidx.activity.result.ActivityResult, context: Context, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                Log.d("GOOGLE_AUTH", "Token generado: $idToken")
                isLoading = true
                val request = GoogleAuthRequest(idToken)
                AuthApiService.authGoogle(request, context) { response ->
                    isLoading = false
                    if (response != null) {
                        onSuccess()
                    } else {
                        onError("Error al autenticar con Google")
                    }
                }
            } else {
                onError("No se recibió el ID Token")
            }
        } catch (e: ApiException) {
            val errorCode = e.statusCode
            val errorMessage = getGoogleErrorDescription(errorCode) // 👈 Usa la función aquí
            Log.e("GOOGLE_AUTH", "Error $errorCode: $errorMessage")
            onError(errorMessage) // Muestra el mensaje traducido al usuario
                   }
    }
    // Método para traducir códigos de error de Google Sign-In
    private fun getGoogleErrorDescription(errorCode: Int): String {
        return when (errorCode) {
            GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> "El usuario canceló el inicio de sesión"
            GoogleSignInStatusCodes.SIGN_IN_FAILED -> "Error interno (reinicia la app)"
            GoogleSignInStatusCodes.SIGN_IN_CURRENTLY_IN_PROGRESS -> "Ya hay un inicio de sesión en curso"
            CommonStatusCodes.API_NOT_CONNECTED -> "Google API no conectada"
            CommonStatusCodes.DEVELOPER_ERROR -> "Error de configuración del desarrollador (verifica SHA-1 y package name)"
            CommonStatusCodes.INTERNAL_ERROR -> "Error interno de Google"
            CommonStatusCodes.INVALID_ACCOUNT -> "Cuenta inválida"
            CommonStatusCodes.SIGN_IN_REQUIRED -> "El usuario no ha iniciado sesión"
            CommonStatusCodes.TIMEOUT -> "Tiempo de conexión agotado"
            10 -> "Configuración incorrecta: verifica SHA-1, package name o ID de cliente"
            12500 -> "App no registrada en Google Cloud o Firebase"
            7 -> "Error de conexión (actualiza Google Play Services)"
            else -> "Error desconocido (Código: $errorCode)"
        }
    }

}