package com.example.haltura.Models

import android.os.Parcelable
import com.example.haltura.Sql.Items.AddresSerializable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorkSerializable(
    @SerializedName("publisher") var publisher: String,
    @SerializedName("company") var company: String,
    @SerializedName("task") var task: String,
    @SerializedName("salary") var salary: Int,
    @SerializedName("numberOfWorkers") var numberOfWorkers: Int,
    @SerializedName("address") var address: AddresSerializable,
    @SerializedName("info") var info: String,
    @SerializedName("startTime") var startTime: String,
    @SerializedName("endTime") var endTime: String,
    @SerializedName("image") var image: String,
    @SerializedName("id") var id: String? = null,
) : Parcelable
