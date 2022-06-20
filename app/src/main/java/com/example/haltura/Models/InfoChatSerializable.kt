package com.example.haltura.Models

import android.os.Parcelable
import com.example.haltura.Sql.Items.MessageSerializable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InfoChatSerializable(
    @SerializedName("id") var id: String? = null,
    @SerializedName("adminID") var adminID: String? = null,
    @SerializedName("chatName") var chatName: String? = null,
    @SerializedName("chatImage") var chatImage: String? = null,
    @SerializedName("members") var members: List<String>? = null,
    @SerializedName("messages") var messages: String? = null,
    @SerializedName("profile_list") var profileList: List<ProfileSerializable>? = null,
) : Parcelable