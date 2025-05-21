package com.example.proyecto.data.services

import android.content.Context
import android.util.Log
import com.example.login.objects.RetrofitInstance
import com.example.proyecto.data.interfaces.notifications.NotificationApiRep
import com.example.proyecto.model.notification.Notification
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object NotificationApiService: NotificationApiRep {
    override fun getNotifications(
        context: Context,
        callback: (List<Notification>?) -> Unit
    ) {
        RetrofitInstance.getNotification(context).getNotifications().enqueue(object : Callback<List<Notification>>{
            override fun onResponse(
                call: Call<List<Notification>?>,
                response: Response<List<Notification>?>
            ) {
                if(response.isSuccessful){
                    callback(response.body())
                    Log.d("NOTIFICATION_RESPONSE:","${response.body()}")

                }else{
                    callback(null)
                    Log.d("NOTIFICATION_RESPONSE:","Notificaciones recibidas:${response.body()}")

                }
            }

            override fun onFailure(
                call: Call<List<Notification>?>,
                t: Throwable
            ) {
                callback(null)
                Log.d("NOTIFICATION_RESPONSE:","Error:${t.message}")
            }

        })
    }
}