package com.example.plansppc.data.model

data class PlanDetail(
    val id: Long = 0,
    val rawId: String = "",
    val motivo: String = "",
    val meta: Double = 0.0,
    val meses: Int = 0,
    val totalAhorrado: Double = 0.0,
    val miembros: List<MiembroSummary> = emptyList()
)
