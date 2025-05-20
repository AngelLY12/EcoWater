package com.example.proyecto.data.interfaces.tanksFilling

import android.content.Context
import com.example.proyecto.model.tankFilling.TankFilling

interface TanksFillingApiRep {
    fun getMainTankFillingsByDate(
        context: Context,
        date: String, // formato yyyy-MM-dd
        callback: (List<TankFilling>?) -> Unit
    )

    fun getMainTankFillingsByDateTime(
        context: Context,
        date: String, // formato yyyy-MM-dd
        startHour: String, // formato HH:mm
        endHour: String, // formato HH:mm
        callback: (List<TankFilling>?) -> Unit
    )

    fun getMainTankFillingsByDateTime(
        context: Context,
        startDate: String, // formato yyyy-MM-dd
        endDate: String, // formato yyyy-MM-dd
        startHour: String, // formato HH:mm
        endHour: String, // formato HH:mm
        callback: (List<TankFilling>?) -> Unit
    )
}