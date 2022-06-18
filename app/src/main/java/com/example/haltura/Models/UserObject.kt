package com.example.haltura.Models

import android.os.Parcelable
import com.example.haltura.Sql.Items.BusinessSerializable
import com.example.haltura.Sql.Items.ProfileSerializable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserObject(
    @SerializedName("id") var userId: String,
    @SerializedName("email") var email: String,
    @SerializedName("token") var token: String,
    @SerializedName("profile") var profile: ProfileSerializable?,
    @SerializedName("business") var business: BusinessSerializable?
) : Parcelable