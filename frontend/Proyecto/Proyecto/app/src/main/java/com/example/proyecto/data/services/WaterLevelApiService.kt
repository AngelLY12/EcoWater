package com.example.proyecto.data.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.login.objects.RetrofitInstance
import com.example.proyecto.data.interfaces.levels.WaterLevelApiRep
import com.example.proyecto.model.level.LevelRequest
import com.example.proyecto.model.level.LevelResponse
import com.example.proyecto.model.level.Levels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

object WaterLevelApiService: WaterLevelApiRep {
    override fun addLevel(
        waterTankLevel: LevelRequest,
        context: Context,
        callback: (Levels?) -> Unit
    ) {
        RetrofitInstance.getWaterLevel(context).addLevel(waterTankLevel).enqueue(object : Callback<Levels>{
            override fun onResponse(
                call: Call<Levels?>,
                response: Response<Levels?>
            ) {
                if(response.isSuccessful){
                    callback(response.body())
                    Log.d("WATER_LEVEL_RESPONSE:","${response.body()}")

                }else{
                    callback(null)
                    Log.d("WATER_LEVEL_RESPONSE:","${response.body()}")

                }
            }

            override fun onFailure(
                call: Call<Levels?>,
                t: Throwable
            ) {
                callback(null)
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
                Log.e("API_ERROR", "Error de conexión", t)            }

        })
    }

    override fun getLevelsMain(
        context: Context,
        callback: (List<Levels>?) -> Unit
    ) {
        RetrofitInstance.getWaterLevel(context).getLevelsMain()
            .enqueue(object : Callback<List<Levels>> {
                override fun onResponse(
                    call: Call<List<Levels>>,
                    response: Response<List<Levels>>
                ) {
                    if (response.isSuccessful) {
                        callback(response.body())
                        Log.d("LEVELS_API", "${response.body()}")
                    } else {
                        callback(null)

                    }
                }

                override fun onFailure(
                    call: Call<List<Levels>>,
                    t: Throwable
                ) {
                    callback(null)
                    Toast.makeText(context, "Fallo de conexión", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Error de conexión", t)
                }

            })
    }

    override fun getLevelsMainByDate(
        context: Context,
        date: String, // formato: "yyyy-MM-dd"
        callback: (List<Levels>?) -> Unit
    ) {
        RetrofitInstance.getWaterLevel(context).getLevelsMainByDate(date)
            .enqueue(object : Callback<List<Levels>> {
                override fun onResponse(
                    call: Call<List<Levels>>,
                    response: Response<List<Levels>>
                ) {
                    if (response.isSuccessful) {
                        callback(response.body())
                        Log.d("LEVELS_API", "Data para $date: ${response.body()}")
                    } else {
                        callback(null)
                        Log.w("LEVELS_API", "Respuesta no exitosa: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<Levels>>, t: Throwable) {
                    callback(null)
                    Toast.makeText(context, "Fallo de conexión", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Error de conexión", t)
                }
            })
    }

    override fun getAvailableDatesMain(
        context: Context,
        callback: (List<Date>?) -> Unit // Cambié a Date para ser consistente con el uso de fechas
    ) {
        RetrofitInstance.getWaterLevel(context).getAvailableDatesMain()
            .enqueue(object : Callback<List<Date>> { // La respuesta es de tipo List<Date>
                override fun onResponse(
                    call: Call<List<Date>>,
                    response: Response<List<Date>>
                ) {
                    if (response.isSuccessful) {
                        // Directamente devolvemos la lista de fechas, Retrofit se encarga de convertirlas
                        callback(response.body())
                        Log.d("LEVELS_API", "Fechas disponibles: ${response.body()}")
                    } else {
                        callback(null) // En caso de fallo
                        Log.w("LEVELS_API", "Respuesta no exitosa: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<Date>>, t: Throwable) {
                    callback(null) // En caso de fallo de red o similar
                    Toast.makeText(context, "Fallo de conexión", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Error al obtener fechas", t)
                }
            })
    }

    override fun getLevelsMainByDateTime(
        context: Context,
        date: String, // formato yyyy-MM-dd
        startHour: String, // formato HH:mm
        endHour: String, // formato HH:mm
        callback: (List<Levels>?) -> Unit
    ) {
        RetrofitInstance.getWaterLevel(context).getLevelsMainByDateTime(date, startHour, endHour)
            .enqueue(object : Callback<List<Levels>> {
                override fun onResponse(call: Call<List<Levels>>, response: Response<List<Levels>>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<List<Levels>>, t: Throwable) {
                    callback(null)
                    Toast.makeText(context, "Fallo de conexión", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Error de conexión", t)
                }
            })
    }

    override fun getLevelsMainByDateTime(
        context: Context,
        startDate: String, // formato yyyy-MM-dd
        endDate: String, // formato yyyy-MM-dd
        startHour: String, // formato HH:mm
        endHour: String, // formato HH:mm
        callback: (List<Levels>?) -> Unit
    ) {
        RetrofitInstance.getWaterLevel(context).getLevelsMainByDateTime(startDate, endDate, startHour, endHour)
            .enqueue(object : Callback<List<Levels>> {
                override fun onResponse(call: Call<List<Levels>>, response: Response<List<Levels>>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<List<Levels>>, t: Throwable) {
                    callback(null)
                    Toast.makeText(context, "Fallo de conexión", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Error de conexión", t)
                }
            })
    }

    override fun getLevelsMainByMonth(
        context: Context,
        startDate: String, // formato HH:mm
        endDate: String, // formato HH:mm
        callback: (Map<String, List<Levels>>?) -> Unit
    ) {
        RetrofitInstance.getWaterLevel(context).getLevelsMainByMonth(startDate, endDate)
            .enqueue(object : Callback<Map<String, List<Levels>>> {
                override fun onResponse(
                    call: Call<Map<String, List<Levels>>>,
                    response: Response<Map<String, List<Levels>>>
                ) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        callback(null)
                    }
                }
                override fun onFailure(call: Call<Map<String, List<Levels>>>, t: Throwable) {
                    callback(null)
                    Log.e("API_ERROR", "Error de conexión", t)
                }
            })
    }

    override fun getFirstMeasurementDate(
        context: Context,
        callback: (LevelResponse?) -> Unit // antes era Date?, ahora es Levels?
    ) {
        RetrofitInstance.getWaterLevel(context).getFirstMeasurementLevel() // el método en el controller Retrofit
            .enqueue(object : Callback<LevelResponse> {
                override fun onResponse(call: Call<LevelResponse?>, response: Response<LevelResponse?>) {
                    if (response.isSuccessful) {
                        callback(response.body())
                        Log.d("LEVELS_API", "Primer nivel de medición: ${response.body()}")
                    } else {
                        callback(null)
                        Log.w("LEVELS_API", "Respuesta no exitosa: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<LevelResponse?>, t: Throwable) {
                    callback(null)
                    Toast.makeText(context, "Fallo de conexión", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Error de conexión", t)
                }
            })
    }
}

