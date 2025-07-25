package com.example.login.objects

import android.content.Context
import com.example.proyecto.data.interfaces.alerts.AlertRepController
import com.example.proyecto.data.interfaces.devices.DeviceApiRepController
import com.example.proyecto.data.interfaces.levels.WaterLevelRepController
import com.example.proyecto.data.interfaces.notifications.NotificationRepController
import com.example.proyecto.data.interfaces.sensorDatas.SensorDataRepController
import com.example.proyecto.data.interfaces.users.AuthRepController
import com.example.proyecto.data.interfaces.tanks.TankRepController
import com.example.proyecto.data.interfaces.tanksFilling.TanksFillingRepController
import com.example.proyecto.data.interfaces.users.UserRepController
import com.example.proyecto.data.services.AuthApiService.getToken
import com.example.proyecto.utils.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object RetrofitInstance {
    private const val BASE_URL = "https://ecowater-1.onrender.com/"

    fun provideRetrofit(context: Context): Retrofit{
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context.applicationContext))
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideAuthRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getAuthApi(): AuthRepController{
        return provideAuthRetrofit().create(AuthRepController::class.java)
    }

    fun getUserApi(context: Context): UserRepController{
        return provideRetrofit(context).create(UserRepController::class.java)
    }

    fun getTankApi(context: Context): TankRepController{
        return provideRetrofit(context).create(TankRepController::class.java)
    }
    fun getWaterLevel(context: Context): WaterLevelRepController{
        return provideRetrofit(context).create(WaterLevelRepController::class.java)
    }
    fun getDevice(context: Context): DeviceApiRepController{
        return provideRetrofit(context).create(DeviceApiRepController::class.java)
    }
    fun getSensorData(context: Context): SensorDataRepController{
        return provideRetrofit(context).create(SensorDataRepController::class.java)
    }
    fun getAlert(context: Context): AlertRepController{
        return provideRetrofit(context).create(AlertRepController::class.java)
    }
    fun getNotification(context: Context): NotificationRepController{
        return provideRetrofit(context).create(NotificationRepController::class.java)
    }
    fun getTankFillingApi(context: Context): TanksFillingRepController{
        return provideRetrofit(context).create(TanksFillingRepController::class.java)
    }

}