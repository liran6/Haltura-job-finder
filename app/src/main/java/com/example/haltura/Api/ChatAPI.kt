package com.example.haltura.Api

import com.example.haltura.Models.ChatInfoSerializable
import com.example.haltura.Sql.Items.UserSerializable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ChatAPI {

    @GET("chats/chats/{userId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getAllChatsOfUserId(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ): Call<ResponseBody>

    @GET("chats/messages/{chatId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getAllMessages(
        @Header("Authorization") token: String,
        @Path("chatId") userId: String,
    ): Call<ResponseBody>

    @GET("chats/{chatId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getChatInfo(
        @Header("Authorization") token: String,
        @Path("chatId") userId: String,
    ): Call<ResponseBody>

    @PUT("chats/changeName/{chatId}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun updateChatInfo(
        @Header("Authorization") token: String,
        @Path("chatId") userId: String,
        @Body chatInfo: ChatInfoSerializable
    ): Call<ResponseBody>
}