package com.example.proyecto.data.interfaces.levels

import android.content.Context
import com.example.proyecto.model.models.LevelResponse
import com.example.proyecto.model.models.Levels

interface WaterLevelApiRep {
    fun addLevel(level: Levels,context: Context, callback: (Levels?) -> Unit )
    fun getTop(context: Context, callback: (LevelResponse?) -> Unit)
    fun getLevels(context: Context,callback: (List<Levels>?)-> Unit)

}