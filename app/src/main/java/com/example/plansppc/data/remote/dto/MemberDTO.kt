package com.example.plansppc.data.remote.dto

import com.example.plansppc.domain.model.Member
import com.google.gson.annotations.SerializedName

data class MemberDTO (
    @SerializedName("_id") val id: String? = null,
    @SerializedName("planId") val planId: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("contributionPerMonth") val contributionPerMonth: Double? = null,
    @SerializedName("joinedAt") val joinedAt: String? = null
) {
    fun toDomain(): Member = Member(
        id = id.orEmpty(),
        planId = planId.orEmpty(),
        name = name.orEmpty(),
        contributionPerMonth = contributionPerMonth ?: 0.0,
        joinedAt = joinedAt.orEmpty()
    )
}