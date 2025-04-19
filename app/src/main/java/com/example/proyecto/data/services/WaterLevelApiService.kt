package com.example.proyecto.data.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.login.objects.RetrofitInstance
import com.example.proyecto.data.interfaces.levels.WaterLevelApiRep
import com.example.proyecto.model.models.LevelResponse
import com.example.proyecto.model.models.Levels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WaterLevelApiService: WaterLevelApiRep {
    override fun addLevel(
        level: Levels,
        context: Context,
        callback: (Levels?) -> Unit
    ) {
        RetrofitInstance.getWaterLevel(context).addLevel(level).enqueue(object : Callback<Levels>{
            override fun onResponse(
                call: Call<Levels?>,
                response: Response<Levels?>
            ) {
                if(response.isSuccessful){
                    callback(response.body())
                    Log.d("WATER_LEVEL_RESPONSE:","${response.body()}")

                }else{
                    callback(null)
                }
            }

            override fun onFailure(
                call: Call<Levels?>,
                t: Throwable
            ) {
                callback(null)
                Toast.makeText(context, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Error de conexión", t)
            }

        })
    }


    override fun getTop(
        context: Context,
        callback: (LevelResponse?) -> Unit,
    ) {
        RetrofitInstance.getWaterLevel(context).getTopLevel().enqueue(object:
            Callback<LevelResponse> {
            override fun onResponse(
                call: Call<LevelResponse?>,
                response: Response<LevelResponse?>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())

                } else {
                    callback(null)
                }
            }

            override fun onFailure(
                call: Call<LevelResponse?>,
                t: Throwable
            ) {
                callback(null)
                Toast.makeText(context, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Error de conexión", t)
            }
        })
    }

    override fun getLevels(
        context: Context,
        callback: (List<Levels>?) -> Unit
    ) {
        RetrofitInstance.getWaterLevel(context).getLevels().enqueue(object : Callback<List<Levels>>{
            override fun onResponse(
                call: Call<List<Levels>>,
                response: Response<List<Levels>>
            ) {
                if(response.isSuccessful){
                    callback(response.body())
                    Log.d("LEVELS_API", "${response.body()}")
                }else{
                    callback(null)

            }
            }

            override fun onFailure(
                call: Call<List<Levels>>,
                t: Throwable
            ) {
                callback(null)
                Toast.makeText(context, "Fallo de conexión", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Error de conexión", t)            }

        })
    }
}

