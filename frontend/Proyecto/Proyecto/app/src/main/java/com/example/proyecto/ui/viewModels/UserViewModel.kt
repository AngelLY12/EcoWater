package com.example.proyecto.ui.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.login.models.User
import com.example.login.services.UserApiService.getProfile
import com.example.login.services.UserApiService.updateProfile

class UserViewModel() : ViewModel() {

    private val _userProfile = mutableStateOf<User?>(null)
    val userProfile: State<User?> = _userProfile

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun loadUserProfile(context: Context) {
        _isLoading.value = true
        getProfile(context) { user ->
            if (user == null) {
                _isLoading.value = false
                Toast.makeText(context, "No se pudo cargar el perfil. Intente nuevamente.", Toast.LENGTH_SHORT).show()
            } else {
                _userProfile.value = user
                _isLoading.value = false
            }
        }
    }

    fun updateUserProfile(context: Context, user: User, onResult: ((User?) -> Unit)? = null) {
        _isLoading.value = true
        updateProfile(user, context) { response ->
            _isLoading.value = false
            if (response != null) {
                _userProfile.value = response
                onResult?.invoke(response)
            } else {
                onResult?.invoke(null)
            }
        }
    }
}