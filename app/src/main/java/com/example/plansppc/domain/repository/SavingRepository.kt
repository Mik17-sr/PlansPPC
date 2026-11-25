package com.example.plansppc.domain.repository

import com.example.plansppc.domain.model.SavingPlan

interface SavingRepository {
    suspend fun getPlans(): Result<List<SavingPlan>>
    suspend fun getPlanById(planId: String): Result<SavingPlan>
    suspend fun createPlan(plan: SavingPlan): Result<SavingPlan>
    suspend fun registerPayment(planId: String, memberId: String, amount: Double): Result<Unit>
}