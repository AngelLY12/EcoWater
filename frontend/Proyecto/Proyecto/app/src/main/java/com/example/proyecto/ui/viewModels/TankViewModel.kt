package com.example.proyecto.ui.viewModels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyecto.data.services.TankApiService.getAll
import com.example.proyecto.model.models.Tank
import androidx.compose.runtime.State
class TankViewModel : ViewModel() {

    private val _tanks = mutableStateOf<List<Tank>>(emptyList())
    val tanks: State<List<Tank>> = _tanks

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun loadTanks(context: Context) {
        _isLoading.value = true
        getAll(context) { tankList ->
            _tanks.value = tankList ?: emptyList()
            _isLoading.value = false
            Log.d("TankViewModel", "Tanques recibidos: ${_tanks.value}")
        }
    }

    fun updateTankInList(updatedTank: Tank) {
        _tanks.value = _tanks.value.map {
            if (it.tankId == updatedTank.tankId) updatedTank else it
        }
    }
}
