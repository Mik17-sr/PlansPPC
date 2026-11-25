package com.example.plansppc.data.remote.dto

import com.example.plansppc.domain.model.SavingPlan
import com.google.gson.annotations.SerializedName

data class PlanDTO (
    @SerializedName("_id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("motive") val motive: String? = null,
    @SerializedName("targetAmount") val targetAmount: Double? = null,
    @SerializedName("months") val months: Int? = null,
    @SerializedName("createdAt") val createdAt: String? = null
) {
    fun toDomain() : SavingPlan = SavingPlan(
        id = id.orEmpty(),
        name = name.orEmpty(),
        motive = motive.orEmpty(),
        targetAmount = targetAmount ?: 0.0,
        months = months ?: 0,
        createdAt = createdAt.orEmpty()
    )
}