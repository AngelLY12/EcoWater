package com.example.proyecto.ui.viewModels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyecto.data.services.WaterLevelApiService
import com.example.proyecto.model.level.LevelResponse
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.proyecto.model.level.Levels
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

class WaterTankViewModel: ViewModel() {
    private val _levelState = mutableStateOf<LevelResponse?>(null)
    val levelState: State<LevelResponse?> = _levelState

    private val _levelsListState = mutableStateOf<List<Levels>>(emptyList())
    val levelsListState: State<List<Levels>> = _levelsListState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _availableDates = mutableStateOf<List<Date>>(emptyList())
    val availableDates: State<List<Date>> = _availableDates

    private val _levelsMapState = mutableStateOf<Map<String, List<Levels>>>(emptyMap())

    private val _firstMeasurementDateState = mutableStateOf<LevelResponse?>(null)
    val firstMeasurementDateState: State<LevelResponse?> = _firstMeasurementDateState


    //private var levelJob: Job? = null
    //private var levelsJob: Job? = null

    fun loadData(context: Context) {
        //levelJob?.cancel()
        //levelJob = viewModelScope.launch {
         //  while (isActive) {
               _isLoading.value = true
                WaterLevelApiService.getTop(context) { response ->
                    _levelState.value = response
                    _isLoading.value = false

                }
           //     delay(900_000)
            //}
    //}

    }

    fun loadLevels(context: Context) {
        //levelsJob?.cancel()
        //levelsJob = viewModelScope.launch {
        //    while (isActive) {
                _isLoading.value = true
                WaterLevelApiService.getLevels(context) { response ->
                    _levelsListState.value = response ?: emptyList()
                    _isLoading.value = false

                }
                //delay(900_000)
            //}
        //}

    }

    /*
    override fun onCleared() {
        super.onCleared()
        levelJob?.cancel()
        levelsJob?.cancel()
    }

     */

    fun loadLevelsMain(context: Context) {
        _isLoading.value = true
        WaterLevelApiService.getLevelsMain(context) { response ->
            _levelsListState.value = response ?: emptyList()
            _isLoading.value = false
        }
    }

    fun loadLevelsMainByDate(context: Context, date: String) {
        _isLoading.value = true
        WaterLevelApiService.getLevelsMainByDate(context, date) { response ->
            _levelsListState.value = response ?: emptyList()
            _isLoading.value = false
        }
    }

    fun loadLevelsMainByDateTime(context: Context, date: String, startHour: String, endHour: String) {
        _isLoading.value = true
        WaterLevelApiService.getLevelsMainByDateTime(context, date, startHour, endHour) { response ->
            _levelsListState.value = response ?: emptyList()
            _isLoading.value = false
        }
    }

    fun loadLevelsMainByDateTime(context: Context, startDate: String, endDate: String, startHour: String, endHour: String) {
        _isLoading.value = true
        WaterLevelApiService.getLevelsMainByDateTime(context, startDate, endDate, startHour, endHour) { response ->
            _levelsListState.value = response ?: emptyList()
            _isLoading.value = false
        }
    }

    // El resto de tu código sigue igual
    fun loadFirstMeasurementDate(context: Context) {
        _isLoading.value = true
        WaterLevelApiService.getFirstMeasurementDate(context) { response ->
            _firstMeasurementDateState.value = response // Aquí lo asignamos correctamente
            _isLoading.value = false
        }
    }

    fun loadLevelsMainByMonth(context: Context, startDate: String, endDate: String) {
        _isLoading.value = true

        WaterLevelApiService.getLevelsMainByMonth(context, startDate, endDate) { response ->
            _levelsMapState.value = response ?: emptyMap()
            _isLoading.value = false
        }
    }

    fun loadAvailableDates(context: Context) {
        // Llamamos al servicio para obtener las fechas disponibles
        WaterLevelApiService.getAvailableDatesMain(context) { response ->
            // Si la respuesta no es nula, asignamos las fechas
            if (response != null) {
                _availableDates.value = response // Asignamos directamente las fechas obtenidas
            } else {
                _availableDates.value = emptyList() // Si la respuesta es nula, dejamos la lista vacía
            }
        }
    }



}