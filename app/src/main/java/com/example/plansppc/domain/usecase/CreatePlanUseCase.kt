package com.example.plansppc.domain.usecase

import com.example.plansppc.domain.model.SavingPlan
import com.example.plansppc.domain.repository.SavingRepository

class CreatePlanUseCase(private val repository: SavingRepository) {
    suspend operator fun invoke(plan: SavingPlan): Result<SavingPlan> {
        if (plan.name.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre del plan no puede estar vacío"))
        }

        if (plan.targetAmount <= 0) {
            return Result.failure(IllegalArgumentException("El monto objetivo debe ser mayor a $0"))
        }

        return repository.createPlan(plan)
    }
}