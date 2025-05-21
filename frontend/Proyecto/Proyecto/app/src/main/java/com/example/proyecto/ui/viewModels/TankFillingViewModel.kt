package com.example.proyecto.ui.viewModels


import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.proyecto.data.services.TankFillingApiService
import com.example.proyecto.model.tankFilling.TankFilling
import java.util.Date

class TankFillingViewModel() : ViewModel() {
    private val _fillingsListState = mutableStateOf<List<TankFilling>>(emptyList())
    val fillingsListState: State<List<TankFilling>> = _fillingsListState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _availableDates = mutableStateOf<List<Date>>(emptyList())
    val availableDates: State<List<Date>> = _availableDates

    fun loadMainTankFillingsByDate(context: Context, date: String) {
        _isLoading.value = true
        TankFillingApiService.getMainTankFillingsByDate(context, date) { response ->
            _fillingsListState.value = response ?: emptyList()
            _isLoading.value = false
        }
    }


    fun loadMainTankFillingsByDateTime(context: Context, date: String, startHour: String, endHour: String) {
        _isLoading.value = true
        TankFillingApiService.getMainTankFillingsByDateTime(context, date, startHour, endHour) { response ->
            _fillingsListState.value = response ?: emptyList()
            _isLoading.value = false
        }
    }

    fun loadMainTankFillingsByDateTime(context: Context, startDate: String, endDate: String, startHour: String, endHour: String) {
        _isLoading.value = true
        TankFillingApiService.getMainTankFillingsByDateTime(context, startDate, endDate, startHour, endHour) { response ->
            _fillingsListState.value = response ?: emptyList()
            _isLoading.value = false
        }
    }

}