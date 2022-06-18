package com.example.haltura.Models

import android.os.Parcelable
import com.example.haltura.Sql.Items.WorksList
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorksByDateMap(
    @SerializedName("works_list") val works_list: Map<String, WorksList>
): Parcelable