package com.example.haltura.Models

import android.os.Parcelable
import com.example.haltura.Sql.Items.BusinessSerializable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusinessesList(
    @SerializedName("business_list")
    val business_list: List<BusinessSerializable>
) : Parcelable