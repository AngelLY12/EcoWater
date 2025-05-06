package com.example.proyecto.data.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.login.objects.RetrofitInstance
import com.example.login.services.UserApiService
import com.example.proyecto.MainActivity
import com.example.proyecto.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import androidx.core.content.edit
import com.example.proyecto.model.alert.AlertRequest

class MyFirebaseMessagingService : FirebaseMessagingService() {



    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        when {
            remoteMessage.data["alert_type"] != null -> handleAlertNotification(remoteMessage)
            remoteMessage.notification != null -> showStandardNotification(remoteMessage)
            else -> Log.e("FCM_DEBUG", "⚠️ Notificación sin contenido útil")
        }
    }

    private fun handleAlertNotification(remoteMessage: RemoteMessage) {
        val alertType = remoteMessage.data["alert_type"]
        val title = remoteMessage.data["title"] ?: remoteMessage.notification?.title ?: "Alerta importante"
        val message = remoteMessage.data["message"] ?: remoteMessage.notification?.body ?: ""

        showNotification(
            title = title,
            message = message,
            alertType = alertType
        )
    }

    private fun showStandardNotification(remoteMessage: RemoteMessage) {
        showNotification(
            title = remoteMessage.notification?.title ?: "Nueva notificación",
            message = remoteMessage.notification?.body ?: "",
            alertType = null
        )
    }

    private fun showNotification(title: String, message: String, alertType: String?) {
        val channelId = "alert_channel_${alertType ?: "general"}"
        val notificationId = System.currentTimeMillis().toInt()

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("from_notification", true)
            alertType?.let { putExtra("alert_type", it) }
        }


        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val (iconRes, vibrationPattern) = when(alertType) {
            "low_level" -> R.drawable.nivel to longArrayOf(500, 200, 500)
            "high_consumption" -> R.drawable.consumo to longArrayOf(300, 100, 300)
            "high_level" -> R.drawable.nivel to longArrayOf(400, 100, 400)
            "medium_level" -> R.drawable.nivel to longArrayOf(200, 100, 200)
            "full_tank" -> R.drawable.deposito_de_agua to longArrayOf(600, 100, 600)
            else -> R.drawable.logo to longArrayOf(300)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId).apply {
            setContentTitle(title)
            setContentText(message)
            setSmallIcon(iconRes)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
            setPriority(NotificationCompat.PRIORITY_MAX)
            setDefaults(NotificationCompat.DEFAULT_SOUND)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setCategory(NotificationCompat.CATEGORY_ALARM)
            setVibrate(vibrationPattern)
        }

        createNotificationChannel(channelId, alertType)

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(notificationId, notificationBuilder.build())

    }

    private fun createNotificationChannel(channelId: String, alertType: String?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channelName = when(alertType) {
            "low_level" -> "Alertas de nivel bajo"
            "high_consumption" -> "Alertas de consumo excesivo"
            "high_level" -> "Alertas de nivel alto"
            "medium_level" -> "Alertas de nivel medio"
            "full_tank" -> "Alerta de tanque lleno"
            else -> "Notificaciones generales"
        }

        NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Canal para $channelName"
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(this)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val prefs = getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val userEmail = prefs.getString("user_email", null)
        if (userEmail != null) {
            sendTokenToServer(token)
        }
    }

    private fun sendTokenToServer(token: String) {
        UserApiService.updateTokenFMC(
            token=token,
            context = applicationContext,
            callback = {
                response->
                if(response!=null){
                    Log.d("FCM_TOKEN", "Token actualizado desde onNewToken: $token")
                }else{
                    Log.e("FCM_TOKEN", "Error al actualizar token")
                    val prefs = getSharedPreferences("fcm_prefs", Context.MODE_PRIVATE)
                    prefs.edit() { putString("pending_token", token) }
                }
            }
        )

    }
    companion object {
        private lateinit var applicationContext: Context

        fun initialize(context: Context) {
            applicationContext = context.applicationContext
            retryPendingToken()
        }

        fun retryPendingToken() {
            applicationContext?.let { ctx ->
                val prefs = ctx.getSharedPreferences("fcm_prefs", Context.MODE_PRIVATE)
                val pendingToken = prefs.getString("pending_token", null)
                pendingToken?.let { token ->
                    UserApiService.updateTokenFMC(
                        token = token,
                        context = ctx,
                        callback = { response ->
                            if (response != null) {
                                prefs.edit { remove("pending_token") }
                            }
                        }
                    )
                }
            }
        }

    }

}