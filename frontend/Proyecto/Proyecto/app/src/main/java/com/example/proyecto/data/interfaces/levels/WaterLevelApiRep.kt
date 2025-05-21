package com.example.proyecto.data.interfaces.levels

import android.content.Context
import com.example.proyecto.model.level.LevelRequest
import com.example.proyecto.model.level.LevelResponse
import com.example.proyecto.model.level.Levels
import java.util.Date

interface WaterLevelApiRep {
    fun addLevel(waterTankLevel: LevelRequest,context: Context, callback: (Levels?) -> Unit )
    fun getTop(context: Context, callback: (LevelResponse?) -> Unit)
    fun getLevels(context: Context,callback: (List<Levels>?)-> Unit)
    fun getLevelsMain(context: Context, callback: (List<Levels>?) -> Unit)
    fun getFirstMeasurementDate(context: Context, callback: (LevelResponse?) -> Unit)

    fun getLevelsMainByDate(
        context: Context,
        date: String, // formato yyyy-MM-dd
        callback: (List<Levels>?) -> Unit
    )
    fun getLevelsMainByDateTime(
        context: Context,
        date: String, // formato yyyy-MM-dd
        startHour: String, // formato HH:mm
        endHour: String, // formato HH:mm
        callback: (List<Levels>?) -> Unit
    )

    fun getLevelsMainByDateTime(
        context: Context,
        startDate: String, // Primer día del rango (yyyy-MM-dd)
        endDate: String,   // Último día del rango (yyyy-MM-dd)
        startHour: String, // Hora de inicio (HH:mm)
        endHour: String,   // Hora de fin (HH:mm)
        callback: (List<Levels>?) -> Unit
    )

    fun getLevelsMainByMonth(
        context: Context,
        startDate: String, // formato HH:mm
        endDate: String, // formato HH:mm
        callback: (Map<String, List<Levels>>?) -> Unit
    )

    fun getAvailableDatesMain(context: Context, callback: (List<Date>?) -> Unit)
}