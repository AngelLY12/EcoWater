package com.example.proyecto.data.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.login.objects.RetrofitInstance
import com.example.proyecto.data.interfaces.tanksFilling.TanksFillingApiRep
import com.example.proyecto.model.tankFilling.TankFilling
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

object TankFillingApiService : TanksFillingApiRep {

    override fun getMainTankFillingsByDate(context: Context, date: String, callback: (List<TankFilling>?) -> Unit) {
        RetrofitInstance.getTankFillingApi(context).getMainTankFillingsByDate(date)
            .enqueue(object : Callback<List<TankFilling>> {
                override fun onResponse(call: Call<List<TankFilling>>, response: Response<List<TankFilling>>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                        Log.d("TANK_FILLING_API", "Registros para $date: ${response.body()}")
                    } else {
                        callback(null)
                        Log.w("TANK_FILLING_API", "Respuesta no exitosa: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<TankFilling>>, t: Throwable) {
                    callback(null)
                    Toast.makeText(context, "Fallo de conexión", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Error de conexión", t)
                }
            })
    }

    override fun getMainTankFillingsByDateTime(
        context: Context,
        date: String, // formato yyyy-MM-dd
        startHour: String, // formato HH:mm
        endHour: String, // formato HH:mm
        callback: (List<TankFilling>?) -> Unit
    ) {
        RetrofitInstance.getTankFillingApi(context).getMainTankFillingsByDateTime(date, startHour, endHour)
            .enqueue(object : Callback<List<TankFilling>> {
                override fun onResponse(call: Call<List<TankFilling>>, response: Response<List<TankFilling>>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                        Log.d("TANK_FILLING_API", "Registros para $date entre $startHour y $endHour: ${response.body()}")
                    } else {
                        callback(null)
                        Log.w("TANK_FILLING_API", "Respuesta no exitosa: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<TankFilling>>, t: Throwable) {
                    callback(null)
                    Toast.makeText(context, "Fallo de conexión", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Error de conexión", t)
                }
            })
    }

    override fun getMainTankFillingsByDateTime(
        context: Context,
        startDate: String, // formato yyyy-MM-dd
        endDate: String, // formato yyyy-MM-dd
        startHour: String, // formato HH:mm
        endHour: String, // formato HH:mm
        callback: (List<TankFilling>?) -> Unit
    ) {
        RetrofitInstance.getTankFillingApi(context).getMainTankFillingsByDateTime(startDate, endDate, startHour, endHour)
            .enqueue(object : Callback<List<TankFilling>> {
                override fun onResponse(call: Call<List<TankFilling>>, response: Response<List<TankFilling>>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<List<TankFilling>>, t: Throwable) {
                    callback(null)
                    Toast.makeText(context, "Error de conexión al obtener llenados", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Error de conexión al obtener llenados", t)
                }
            })
    }

}