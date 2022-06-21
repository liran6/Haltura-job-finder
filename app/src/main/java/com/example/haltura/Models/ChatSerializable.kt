package com.example.haltura.Models

import android.os.Parcelable
import com.example.haltura.Sql.Items.MessageSerializable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatSerializable(
    @SerializedName("id") var id: String? = null,
    @SerializedName("adminID") var adminID: String,
    @SerializedName("chatName") var chatName: String?,
    @SerializedName("chatImage") var chatImage: String?,
    @SerializedName("members") var members: List<String>,
    @SerializedName("messages") var messages: String,
    @SerializedName("mapUsernames") var mapUsernames: Map<String,String>,//added
    @SerializedName("lastMessage") var lastMessage: MessageSerializable,//added
) : Parcelable