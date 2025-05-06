package com.example.proyecto.data.services
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.login.objects.RetrofitInstance
import com.example.proyecto.data.interfaces.alerts.AlertApiRep
import com.example.proyecto.model.alert.AlertRequest
import com.example.proyecto.model.alert.UserAlertSettings
import com.example.proyecto.model.device.Device
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object AlertApiService: AlertApiRep {
    override fun create(
        userAlertSettings: AlertRequest,
        context: Context,
        callback: (AlertRequest?) -> Unit
    ) {
        RetrofitInstance.getAlert(context).saveAlert(userAlertSettings).enqueue(object : Callback<AlertRequest>{
            override fun onResponse(
                call: Call<AlertRequest>,
                response: Response<AlertRequest>
            ) {
                if(response.isSuccessful){
                    Log.e("ALERT_API", "Response: ${response.body()}")
                callback(response.body())
                }else{
                    callback(null)
                    Log.e("ALERT_API", "Response: ${response.body()}")
                }
            }

            override fun onFailure(
                call: Call<AlertRequest>,
                t: Throwable
            ) {
                callback(null)
                Log.e("AUTH_ERROR", "Fallo de conexión: ${t.message}", t)            }

        })
    }

    override fun updateStatus(
        userAlertSettings: UserAlertSettings,
        context: Context,
        callback: (Boolean?) -> Unit
    ) {
        RetrofitInstance.getAlert(context).updateStatus(userAlertSettings).enqueue(object : Callback<Boolean>{
            override fun onResponse(
                call: Call<Boolean?>,
                response: Response<Boolean?>
            ) {
                if(response.isSuccessful){
                    Log.e("ALERT_API", "Response: ${response.body()}")
                    callback(response.body())
                }else{
                    callback(null)
                    Log.e("ALERT_API", "Response: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<Boolean?>, t: Throwable) {
                callback(null)
                Log.e("AUTH_ERROR", "Fallo de conexión: ${t.message}", t)             }

        })
    }

    override fun getAllAlerts(
        context: Context,
        callback: (List<UserAlertSettings>?) -> Unit
    ) {
        RetrofitInstance.getAlert(context).getAlerts().enqueue(object : Callback<List<UserAlertSettings>>{
            override fun onResponse(
                call: Call<List<UserAlertSettings>>,
                response: Response<List<UserAlertSettings>>
            ) {
                if (response.isSuccessful) {
                    val deviceList = response.body()
                    callback(deviceList)
                } else {
                    callback(null)
                    Toast.makeText(context, "No se encontraron alertas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<List<UserAlertSettings>>,
                t: Throwable
            ) {
                callback(null)
                Toast.makeText(context, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Error de conexión", t)            }

        })
    }
}