package com.example.proyecto.ui.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyecto.data.services.TankApiService.getAll
import com.example.proyecto.data.services.TankApiService.deleteTank
import com.example.proyecto.data.services.TankApiService.updateTank

import com.example.proyecto.model.models.Tank
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyecto.ui.components.ToastType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


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

    fun deleteTank(tankId: Long, context: Context) {
        _isLoading.value = true

        deleteTank(tankId, context) { response ->
            _isLoading.value = false
            if (response != null && response != "Failed") {
                _tanks.value = _tanks.value.filter { it.tankId != tankId }
                Toast.makeText(context,"Tanque eliminado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Error al eliminar", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun updateTank(context: Context, tank: Tank) {
        _isLoading.value = true
        updateTank(context = context, tank = tank) { response ->
            _isLoading.value = false
            if (response != null) {
                updateTankInList(tank)
                Toast.makeText(context,"Campo actualizado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Error al actualizar", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
