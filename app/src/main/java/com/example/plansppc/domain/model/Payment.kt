package com.example.plansppc.domain.model

data class Payment (
    val id: String = "",
    val memberId: String = "",
    val planId: String = "",
    val amount: Double = 0.0,
    val date: String = "",
)