package com.example.plansppc.data.remote.dto

import com.example.plansppc.domain.model.Payment
import com.google.gson.annotations.SerializedName

data class PaymentDTO (
    @SerializedName("_id") val id: String? = null,
    @SerializedName("memberId") val memberId: String? = null,
    @SerializedName("planId") val planId: String? = null,
    @SerializedName("amount") val amount: Double? = null,
    @SerializedName("date") val date: String? = null
) {
    fun toDomain(): Payment = Payment (
        id = id.orEmpty(),
        memberId = memberId.orEmpty(),
        planId = planId.orEmpty(),
        amount = amount ?: 0.0,
        date = date.orEmpty(),
    )
}