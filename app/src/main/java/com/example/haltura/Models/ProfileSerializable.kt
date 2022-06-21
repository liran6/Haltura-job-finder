package com.example.haltura.Models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileSerializable(
    @SerializedName("userId") var userId: String?,
    @SerializedName("firstName") var firstName: String?,
    @SerializedName("lastName") var lastName: String?,
    @SerializedName("phone") var phone: String?,
    @SerializedName("address") var address: String?,
    @SerializedName("chatList") var chatList: List<String>?,
    @SerializedName("workList") var workList: List<String>?,
    @SerializedName("businessList") var businessList: List<String>?,
    @SerializedName("id") var id: String?,
    @SerializedName("email") var email: String,
    @SerializedName("username") var username: String,
) : Parcelable