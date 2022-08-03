package com.example.haltura.Api

import com.example.haltura.Models.ReportSerializable
import com.example.haltura.Sql.Items.UserLoginSerializable
import com.example.haltura.Sql.Items.WorkSerializable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ReportApi {
    //USERS

    @DELETE("report/{reportId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun removeReport(
        @Header("Authorization") token: String,
        @Path("reportId") workId: String,
    ): Call<ResponseBody>

    @POST("report/submit")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun submitReport(
        @Header("Authorization") token: String,
        @Body report: ReportSerializable
    ): Call<ResponseBody>

    @GET("report/all")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getAllReports(
        @Header("Authorization") token: String,
    ): Call<ResponseBody>
}