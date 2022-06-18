package com.example.haltura.Models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class UserLoginSerializable {
    @Parcelize
    data class UserLoginSerializable(
        @SerializedName("email") var email: String,
        @SerializedName("password") var password: String
    ) : Parcelable
}