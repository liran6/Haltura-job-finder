package com.example.haltura.Models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddresSerializable(
    @SerializedName("city") var city: String,
    @SerializedName("street") var street: String,
    @SerializedName("streetNum") var streetNum: Int,
    @SerializedName("floor") var floor: Int,
    @SerializedName("appartment") var appartment: String,
) : Parcelable