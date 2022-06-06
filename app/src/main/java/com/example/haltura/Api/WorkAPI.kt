package com.example.haltura.Api
import com.example.haltura.Sql.Items.*
import okhttp3.ResponseBody


import retrofit2.Call
import retrofit2.http.*
interface WorkAPI {
    //Work

    //TODO check if works(not yet)
    @POST("works/create/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun addWork(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body user: WorkSerializable
    ): Call<ResponseBody>

    @PUT("works/update/{workId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun updateWork(
        @Header("Authorization") token: String,
        @Path("workId") workId: String,
        @Body user: WorkSerializable
    ): Call<ResponseBody>

    //TODO what this call do?
    @PUT("works/add/{workId}/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun addToWorkUserId(
        @Header("Authorization") token: String,
        @Path("workId") workId: String,
        @Path("userId") userId: String,
    ): Call<ResponseBody>

    //TODO what this call do?
    @PUT("works/remove/{workId}/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun removeFromWorkUserId(
        @Header("Authorization") token: String,
        @Path("workId") workId: String,
        @Path("userId") userId: String,
    ): Call<ResponseBody>

    // get all works of userId (AS WORKER AND AS EMPLOYER)
    @GET("works/{who}/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getAllWorksOfUserID(
        @Header("Authorization") token: String,
        @Path("who") who: String,
        @Path("userId") userId: String,
    ): Call<ResponseBody>

    //get all works of userId (AS EMPLOYER)
    @GET("works/publish/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getAllWorksOfUserID1(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ): Call<ResponseBody>

    //get all works of userId (AS EMPLOYEE)
    @GET("works/employee/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getAllWorksOfUserID2(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ): Call<ResponseBody>

    //get work by workId
    @GET("works/{workId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getWork(
        @Header("Authorization") token: String,
        @Path("workId") workId: String,
    ): Call<ResponseBody>

    //delete work by workId
    @DELETE("works/delete/{workId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun deleteWork(
        @Header("Authorization") token: String,
        @Path("workId") workId: String,
    ): Call<ResponseBody>

    //delete all works of userId - use this api call when delete user
    @DELETE("works/delete/all/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun deleteAllUsersWork(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ): Call<ResponseBody>
}
