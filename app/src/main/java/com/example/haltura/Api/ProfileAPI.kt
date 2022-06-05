package com.example.haltura.Api
import com.example.haltura.Sql.Items.*
import okhttp3.ResponseBody


import retrofit2.Call
import retrofit2.http.*
interface ProfileAPI {

    @GET("profiles/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getProfile(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): Call<ResponseBody>

    //ToDo check if works after finish.
    @PUT("profiles/update/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun updateProfileInfo(
        @Path("userId") userId: String,
        //@Header("token") token: String,
        @Header("Authorization") token: String,
        @Body user: ProfileSerializable
    ): Call<ResponseBody>

    //Business

    @GET("profiles/all/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getBusinesses(
        @Header("Authorization") token: String,
        @Path("userId") userId: String
    ): Call<ResponseBody>


    @POST("profiles/add/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun addBusiness(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body user: BusinessSerializable
    ): Call<ResponseBody>

    @PUT("profiles/update/{userId}/{businessname}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun updateBusinessInfo(
        @Path("userId") userId: String,
        @Path("businessname") businessname: String,
        //@Header("token") token: String,
        @Header("Authorization") token: String,
        @Body business: BusinessSerializable
    ): Call<ResponseBody>

    @DELETE("profiles/delete/{userId}/{businessname}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun deleteBusiness(
        @Path("userId") userId: String,
        @Path("businessname") businessname: String,
        @Header("Authorization") token: String,
    ): Call<ResponseBody>

    @DELETE("profiles/delete/all/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun deleteAllBusinesses(
        @Path("userId") userId: String,
        @Header("Authorization") token: String,
    ): Call<ResponseBody>
}