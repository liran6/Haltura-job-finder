package com.example.haltura.Api
import com.example.haltura.Models.PromptSerializable
import com.example.haltura.Sql.Items.*
import okhttp3.ResponseBody


import retrofit2.Call
import retrofit2.http.*
interface WorkAPI {
    //Work


    @POST("works/create/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun createWork(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body work: WorkSerializable
    ): Call<ResponseBody>

    @PUT("works/update/{workId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun updateWork(
        @Header("Authorization") token: String,
        @Path("workId") workId: String,
        @Body work: WorkSerializable
    ): Call<ResponseBody>


    @GET("works/all")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getAllWorks(
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Register to work
    @PUT("works/add/{workId}/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun putRegisterToWork(
        @Header("Authorization") token: String,
        @Path("workId") workId: String,
        @Path("userId") userId: String,
    ): Call<ResponseBody>

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
    fun getAllWorksThatUserIdPublished(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ): Call<ResponseBody>

    //get all works of userId (AS EMPLOYER)
    @GET("works/close/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getCloseWorksOfUserId(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ): Call<ResponseBody>

    //get all works of userId (AS EMPLOYER)
    @GET("works/history/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getWorkHistoryOfUserId(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ): Call<ResponseBody>

    //get works with NLP prompt
    @POST("works/nlp")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getNlpWorks(
        @Header("Authorization") token: String,
        @Body prompt: PromptSerializable
    ): Call<ResponseBody>

    //get all works of userId (AS EMPLOYER)- by date!!
    @GET("works/calendar/publish/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getAllWorksThatUserIdPublishedByDate(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ): Call<ResponseBody>

    //get all works of userId (AS EMPLOYEE)- by date!!
    @GET("works/calendar/employee/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getAllWorksThatUserIdRegisterdByDate(
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
