package com.example.proyecto.ui.viewModels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyecto.data.services.WaterLevelApiService
import com.example.proyecto.model.models.LevelResponse
import androidx.compose.runtime.State
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.proyecto.model.models.Levels
import com.example.proyecto.model.models.Tank

class WaterTankViewModel: ViewModel() {
    private val _levelState = mutableStateOf<LevelResponse?>(null)
    val levelState: State<LevelResponse?> = _levelState

    private val _levelsListState = mutableStateOf<List<Levels>>(emptyList())
    val levelsListState: State<List<Levels>> = _levelsListState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun loadData(context: Context) {
        _isLoading.value = true
        WaterLevelApiService.getTop(context) { response ->
            _levelState.value = response
            _isLoading.value = false
        }
    }

    fun loadLevels(context: Context) {
        _isLoading.value = true
        WaterLevelApiService.getLevels(context) { response ->
            _levelsListState.value = response ?: emptyList()
            _isLoading.value = false
        }
    }
}