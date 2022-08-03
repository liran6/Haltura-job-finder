package com.example.haltura.Models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ReportSerializable(
    @SerializedName("id") var reportId: String?,
    @SerializedName("userId") var userId: String?,
    @SerializedName("workId") var workId: String?,
    @SerializedName("reportText") var reportText: String?,
    @SerializedName("reportImage") var reportImage: String?
) : Parcelable
