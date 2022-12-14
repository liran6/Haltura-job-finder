package com.example.haltura.Api

import com.example.haltura.Sql.Items.*
import okhttp3.ResponseBody


import retrofit2.Call
import retrofit2.http.*

interface UsersAPI {
    //USERS

    @POST("users/register")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun createUser(
        @Body params: UserLoginSerializable
    ): Call<ResponseBody>

    @POST("users/authenticate")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun userAuth(
        @Body user: UserLoginSerializable
    ): Call<ResponseBody>

    @GET("users/current")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getUserInfo(
        @Header("Authorization") token: String,
    ): Call<ResponseBody>

    @DELETE("users/{id}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<ResponseBody>

    @PUT("users/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun updateUserPasswordInfo(
        @Path("userId") userId: String,
        //@Header("token") token: String,
        @Header("Authorization") token: String,
        @Body password: UserLoginSerializable
    ): Call<ResponseBody>
}

