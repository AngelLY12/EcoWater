package com.example.proyecto.data.interfaces.users

import android.content.Context
import com.example.login.models.User

interface UserApiRep {
    fun create(user: User, context: Context, callback: (User?) -> Unit)
    fun getProfile(context: Context, callback: (User?) -> Unit)
    fun updateTokenFMC(token: String, context: Context, callback: (String?) -> Unit)

}