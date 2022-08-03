package com.example.haltura.Models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PromptSerializable(
    @SerializedName("prompt") var prompt: String
) : Parcelable