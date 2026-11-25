package com.example.plansppc.data.model

data class PlanSummary(
    val id: Long = 0,
    val rawId: String = "",
    val motivo: String = "",
    val meta: Double = 0.0,
    val meses: Int = 0,
    val totalAhorrado: Double = 0.0,
    val cantidadMiembros: Int = 0
)
