package com.example.trexense.data.retrofit

import com.example.trexense.data.response.LoginResponse
import com.example.trexense.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun createFormRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun formLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

}