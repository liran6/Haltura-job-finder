package com.example.haltura.Models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusinessSerializable(
    @SerializedName("listOfWork") var listOfWork: List<String>?,
    @SerializedName("userId") var userId: String,
    @SerializedName("name") var name: String,
    @SerializedName("about") var about: String,
    @SerializedName("image") var image: String,
    @SerializedName("id") var id: String,
) : Parcelable