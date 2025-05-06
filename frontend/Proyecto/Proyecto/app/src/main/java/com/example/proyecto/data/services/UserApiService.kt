package com.example.login.services

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.login.models.User
import com.example.login.objects.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import com.example.proyecto.data.interfaces.users.UserApiRep
import okhttp3.ResponseBody


object UserApiService : UserApiRep {
    override fun create(item: User,
                        context: Context,
                        callback: (User?) -> Unit
    ) {
        RetrofitInstance.getAuthApi().registerUser(item).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    callback(authResponse)
                    Toast.makeText(context, "Usuario registrado", Toast.LENGTH_SHORT).show()
                } else {
                    callback(null)
                    Toast.makeText(context, "Correo ya registrado", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null)
                Toast.makeText(context, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Error de conexión", t)
            }
        })
    }

    override fun updateTokenFMC(
        token: String,
        context: Context,
        callback: (String?) -> Unit
    ) {
        RetrofitInstance.getUserApi(context).updateFMCToken(token).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if(response.isSuccessful){
                    callback(response.body().toString())
                    Toast.makeText(context, "Nuevo token registrado: ${response.body().toString()}", Toast.LENGTH_SHORT).show()
                }else{
                    callback(null)
                    Log.d("TOKEN_FCM", "Hubo un inconveniente")
                }
            }

            override fun onFailure(
                call: Call<ResponseBody?>,
                t: Throwable
            ) {
                callback(null)
                Toast.makeText(context, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Error de conexión", t)            }

        })
    }


}







