package com.example.proyecto.ui.viewModels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyecto.data.services.WaterLevelApiService
import com.example.proyecto.model.models.LevelResponse
import androidx.compose.runtime.State
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewModelScope
import com.example.proyecto.model.models.Levels
import com.example.proyecto.model.models.Tank
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WaterTankViewModel: ViewModel() {
    private val _levelState = mutableStateOf<LevelResponse?>(null)
    val levelState: State<LevelResponse?> = _levelState

    private val _levelsListState = mutableStateOf<List<Levels>>(emptyList())
    val levelsListState: State<List<Levels>> = _levelsListState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    //private var levelJob: Job? = null
    //private var levelsJob: Job? = null

    fun loadData(context: Context) {
        //levelJob?.cancel()
        //levelJob = viewModelScope.launch {
         //   while (isActive) {
        //        _isLoading.value = true
                WaterLevelApiService.getTop(context) { response ->
                    _levelState.value = response
               // }
                _isLoading.value = false
           //     delay(5000)
            }
    //}

    }

    fun loadLevels(context: Context) {
        //levelsJob?.cancel()
        //levelsJob = viewModelScope.launch {
          //  while (isActive) {
                _isLoading.value = true
                WaterLevelApiService.getLevels(context) { response ->
                    _levelsListState.value = response ?: emptyList()
                //}
                _isLoading.value = false
                //delay(5000)
            }
        //}

    }
    /*
    override fun onCleared() {
        super.onCleared()
        levelJob?.cancel()
        levelsJob?.cancel()
    }

     */

}