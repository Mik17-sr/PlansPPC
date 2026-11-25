package com.example.plansppc.data.repository

import com.example.plansppc.data.db.DatabaseHelper
import com.example.plansppc.data.model.MiembroEntity
import com.example.plansppc.data.model.PlanDetail
import com.example.plansppc.data.model.PlanSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SavingsRepository(private val dbHelper: DatabaseHelper) {

    suspend fun crearPlan(motivo: String, meta: Double, meses: Int, miembros: List<String>): Long {
        return withContext(Dispatchers.IO) {
            dbHelper.crearPlanConMiembros(motivo, meta, meses, miembros)
        }
    }

    suspend fun getPlanesSummary(): List<PlanSummary> {
        return withContext(Dispatchers.IO) {
            dbHelper.getPlanesSummary()
        }
    }

    suspend fun getPlanDetail(planId: Long): PlanDetail? {
        return withContext(Dispatchers.IO) {
            dbHelper.getPlanDetail(planId)
        }
    }

    suspend fun getMiembrosForPlan(planId: Long): List<MiembroEntity> {
        return withContext(Dispatchers.IO) {
            dbHelper.getMiembrosForPlan(planId)
        }
    }

    suspend fun registrarPago(planId: Long, miembroId: Long, monto: Double): Boolean {
        return withContext(Dispatchers.IO) {
            dbHelper.registrarPago(planId, miembroId, monto)
        }
    }
}
