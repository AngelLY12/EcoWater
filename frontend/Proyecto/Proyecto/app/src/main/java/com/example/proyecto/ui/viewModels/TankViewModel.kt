package com.example.proyecto.ui.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyecto.data.services.TankApiService.getAll
import com.example.proyecto.data.services.TankApiService.deleteTank
import com.example.proyecto.data.services.TankApiService.updateTank

import com.example.proyecto.model.tank.Tank
import androidx.compose.runtime.State
import com.example.proyecto.data.services.DeviceApiService
import com.example.proyecto.data.services.TankApiService
import com.example.proyecto.model.device.Device


class TankViewModel () : ViewModel() {

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

    fun deleteTank(tankId: Long, context: Context, onResult: ((String) -> Unit)?=null) {
        _isLoading.value = true

        TankApiService.deleteTank(tankId, context) { response ->
            _isLoading.value = false
            if (response != null && response != "Failed") {
                _tanks.value = _tanks.value.filter { it.tankId != tankId }
                onResult?.invoke("Exito")
            } else {
                onResult?.invoke("Fallo")

            }
        }
    }

    fun updateTank(context: Context, tank: Tank, onResult: ((Boolean) -> Unit)?=null) {
        _isLoading.value = true
        TankApiService.updateTank(context = context, tank = tank) { response ->
            _isLoading.value = false
            if (true) {
                updateTankInList(tank)
                onResult?.invoke(true)
            } else {
                onResult?.invoke(false)

            }
        }
    }




}
