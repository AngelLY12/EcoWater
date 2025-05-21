package com.example.proyecto.data.interfaces.users

import com.example.login.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserRepController {
    @POST("user/updateToken/{token}")
    fun updateFMCToken(@Path("token")token: String): Call<ResponseBody>

    @GET("user/getUser")
    fun getUserProfile(): Call<User>

    @PATCH("user/updateUser")
    fun updateUser(@Body user: User): Call<User>
}