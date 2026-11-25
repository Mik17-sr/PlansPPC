package com.example.plansppc.domain.model

data class Member (
    val id: String = "",
    val planId: String = "",
    val name: String = "",
    val contributionPerMonth: Double = 0.0,
    val joinedAt: String = ""
)