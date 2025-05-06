package com.example.proyecto.data.interfaces.users

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface UserRepController {
    @POST("user/updateToken/{token}")
    fun updateFMCToken(@Path("token")token: String): Call<ResponseBody>
}