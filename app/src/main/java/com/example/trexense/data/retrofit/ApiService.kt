package com.example.trexense.data.retrofit

import com.example.trexense.data.response.CreatePlanResponse
import com.example.trexense.data.response.EventResponse
import com.example.trexense.data.response.LoginResponse
import com.example.trexense.data.response.PlanDetailResponse
import com.example.trexense.data.response.PlansResponse
import com.example.trexense.data.response.RegisterResponse
import com.example.trexense.data.response.SearchResponse
import com.example.trexense.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("auth/register")
    fun createFormRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun formLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("ads/banners")
    suspend fun getEvent(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ) : EventResponse

    @GET("plans")
    suspend fun getPlans(): PlansResponse

    @FormUrlEncoded
    @POST("plans/create")
    suspend fun createPlan(
        @Field("name") name: String,
        @Field("startDate") startDate: String,
        @Field("endDate") endDate: String,
    ): CreatePlanResponse

    @GET("hotels/search")
    suspend fun searchHotel(
        @Query("name") name: String
    ): SearchResponse

    @FormUrlEncoded
    @PATCH("user/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: String,
        @Field("name") name: String ?= "",
        @Field("email") email: String ?= ""
    ) : UserResponse

    @GET("plans/{id}")
    suspend fun getDetailPlan(
        @Path("id") id: String
    ): PlanDetailResponse

}