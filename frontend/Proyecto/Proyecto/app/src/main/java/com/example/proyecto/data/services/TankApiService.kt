package com.example.proyecto.data.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.login.objects.RetrofitInstance
import com.example.proyecto.data.interfaces.tanks.TankApiRep
import com.example.proyecto.model.tank.Tank
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object TankApiService: TankApiRep {
    override fun create(
        item: Tank,
        context: Context,
        callback: (Tank?)-> Unit
    ) {
        RetrofitInstance.getTankApi(context).createTank(item).enqueue(object: Callback<Tank>{
            override fun onResponse(call: Call<Tank>, response: Response<Tank>) {
                if (response.isSuccessful) {
                    val successResponse= response.body()
                    callback(successResponse)
                } else {
                    callback(null)

                }
            }

            override fun onFailure(call: Call<Tank>, t: Throwable) {
                callback(null)
                Log.e("API_ERROR", "Error de conexión", t)
            }
        })
    }

    override fun getAll(
        context: Context,
        callback: (List<Tank>?) -> Unit
    ) {
        RetrofitInstance.getTankApi(context).getTanks().enqueue(object : Callback<List<Tank>>{
            override fun onResponse(call: Call<List<Tank>>, response: Response<List<Tank>>) {
                if (response.isSuccessful) {
                    val tankList = response.body()
                    callback(tankList)
                } else {
                    callback(null)  // Si hubo error en la respuesta
                }
            }

            override fun onFailure(call: Call<List<Tank>>, t: Throwable) {
                callback(null)  // Si falla la conexión
                Log.e("API_ERROR", "Error de conexión", t)
            }
        })
    }

    override fun updateTank(
        tank: Tank,
        context: Context,
        callback: (Tank?)-> Unit
    ) {
        RetrofitInstance.getTankApi(context).updateTank(tank).enqueue(object : Callback<Tank>{
            override fun onResponse(
                call: Call<Tank?>,
                response: Response<Tank?>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }            }

            override fun onFailure(
                call: Call<Tank?>,
                t: Throwable
            ) {
                callback(null)
                Log.e("API_ERROR", "Error de conexión", t)            }

        })
    }

    override fun deleteTank(
        tankId: Long,
        context: Context,
        callback: (String?) -> Unit
    ) {
        RetrofitInstance.getTankApi(context).deleteTank(tankId).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if(response.isSuccessful){
                    callback("Tanque eliminado correctamente")
                }else{
                    callback("Failed")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback(null)
                Log.e("API_ERROR", "Error de conexión", t)                      }

        })
    }


}