package com.example.proyecto.data.interfaces.tanks

import android.content.Context
import com.example.proyecto.model.models.Tank

interface TankApiRep{
    fun create(tank: Tank , context: Context, callback: (Tank?)-> Unit)
    fun getAll(context: Context, callback: (List<Tank>?) -> Unit)
    fun updateTank(tank: Tank, context: Context, callback: (Tank?)-> Unit)
    fun deleteTank(tankId: Long,context: Context,callback: (String?) -> Unit)
}