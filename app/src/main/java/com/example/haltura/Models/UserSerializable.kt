package com.example.haltura.Models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserSerializable(
    @SerializedName("email") var email: String,
    @SerializedName("createdDate") var createdDate: String?,
    @SerializedName("id") var id: String,
    @SerializedName("token") var token: String,
    @SerializedName("password") var password: String?
) : Parcelable