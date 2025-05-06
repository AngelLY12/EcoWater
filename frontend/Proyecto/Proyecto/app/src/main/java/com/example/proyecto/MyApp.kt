package com.example.proyecto

import android.app.Application
import android.content.Context
import com.example.proyecto.data.services.MyFirebaseMessagingService
import com.google.firebase.FirebaseApp

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        MyFirebaseMessagingService.initialize(this)
        MyFirebaseMessagingService.retryPendingToken()

    }

}