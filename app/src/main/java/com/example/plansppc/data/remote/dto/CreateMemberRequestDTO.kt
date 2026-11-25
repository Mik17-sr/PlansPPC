package com.example.plansppc.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateMemberRequestDTO(
    @SerializedName("name") val name: String,
    @SerializedName("planId") val planId: String,
    @SerializedName("contributionPerMonth") val contributionPerMonth: Double? = null
)
