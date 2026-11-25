package com.example.plansppc.data.repository

import com.example.plansppc.data.remote.SavingApiService
import com.example.plansppc.data.remote.dto.PaymentDTO
import com.example.plansppc.data.remote.dto.PlanDTO
import com.example.plansppc.domain.model.SavingPlan
import com.example.plansppc.domain.repository.SavingRepository

class AhorroRepositoryImpl(private val api: SavingApiService): SavingRepository {

    override suspend fun getPlans(): Result<List<SavingPlan>> = try {
        val response = api.getPlans()
        Result.success(response.map { it.toDomain() })
    } catch(e: Exception) {
        Result.failure(e)
    }

    override suspend fun getPlanById(planId: String): Result<SavingPlan> = try {
        Result.success(api.getPlanById(planId).toDomain())
    } catch(e: Exception) {
        Result.failure(e)
    }

    override suspend fun createPlan(plan: SavingPlan): Result<SavingPlan> = try {
        val dto = PlanDTO(
            name = plan.name,
            motive = plan.motive,
            targetAmount = plan.targetAmount,
            months = plan.months
        )

        val response = api.createPlan(dto)
        Result.success(response.toDomain())
    } catch(e: Exception) {
        Result.failure(e)
    }

    override suspend fun registerPayment(
        planId: String,
        memberId: String,
        amount: Double
    ) : Result<Unit> = try {
        val dto = PaymentDTO(
            planId = planId,
            memberId = memberId,
            amount = amount
        )

        api.registerPayment(dto)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}