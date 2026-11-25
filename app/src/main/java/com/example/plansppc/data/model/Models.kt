package com.example.plansppc.data.model

data class PlanEntity(
    val id: Long = 0,
    val motivo: String,
    val meta: Double,
    val meses: Int
)

data class MiembroEntity(
    val id: Long = 0,
    val planId: Long,
    val nombre: String
)

data class PagoEntity(
    val id: Long = 0,
    val planId: Long,
    val miembroId: Long,
    val monto: Double,
    val fecha: String
)

// UI Data Transfer Objects
data class PlanSummary(
    val id: Long,
    val motivo: String,
    val meta: Double,
    val meses: Int,
    val totalAhorrado: Double,
    val cantidadMiembros: Int
)

data class MiembroSummary(
    val id: Long,
    val nombre: String,
    val totalPagado: Double
)

data class PlanDetail(
    val id: Long,
    val motivo: String,
    val meta: Double,
    val meses: Int,
    val totalAhorrado: Double,
    val miembros: List<MiembroSummary>
)
