package com.example.haltura.Models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatInfoSerializable(
    @SerializedName("chatName") var chatName: String,
    @SerializedName("chatImage") var chatImage: String,
) : Parcelable