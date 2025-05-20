package com.example.proyecto.data.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.login.objects.RetrofitInstance
import com.example.proyecto.data.interfaces.sensorDatas.SensorDataApiRep
import com.example.proyecto.model.sensor.SensorDataRequest
import com.example.proyecto.model.sensor.SensorDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SensorDataApiService: SensorDataApiRep {
    override fun addData(
        sensorData: SensorDataRequest,
        context: Context,
        callback: (SensorDataRequest?) -> Unit
    ) {
        RetrofitInstance.getSensorData(context).addData(sensorData).enqueue(object : Callback<SensorDataRequest?>{
            override fun onResponse(
                call: Call<SensorDataRequest?>,
                response: Response<SensorDataRequest?>
            ) {
                if(response.isSuccessful){
                    callback(response.body())
                    Log.d("SENSOR_RESPONSE:","${response.body()}")
                }else{
                    callback(null)
                }
            }

            override fun onFailure(
                call: Call<SensorDataRequest?>,
                t: Throwable
            ) {
                callback(null)
                Log.e("API_ERROR", "Error de conexión", t)            }

        })
    }


}