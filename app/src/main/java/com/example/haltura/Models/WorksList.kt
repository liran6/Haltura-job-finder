package com.example.haltura.Models

import android.os.Parcelable
import com.example.haltura.Sql.Items.WorkSerializable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorksList(
    @SerializedName("work_list")
    val work_list: List<WorkSerializable>
) : Parcelable