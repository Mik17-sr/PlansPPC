package com.example.plansppc.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreatePlanRequestDTO(
    @SerializedName("name") val name: String,
    @SerializedName("motive") val motive: String,
    @SerializedName("targetAmount") val targetAmount: Double,
    @SerializedName("months") val months: Int
)
