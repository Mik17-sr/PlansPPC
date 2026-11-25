package com.example.plansppc.domain.model

data class SavingPlan (
    val id: String = "",
    val name: String = "",
    val motive: String = "",
    val targetAmount: Double = 0.0,
    val months: Int = 0,
    val createdAt: String = ""
)