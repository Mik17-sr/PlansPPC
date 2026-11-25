package com.example.plansppc.domain.usecase

import com.example.plansppc.domain.model.SavingPlan
import com.example.plansppc.domain.repository.SavingRepository

class GetDetailsPlanUseCase (private val repository: SavingRepository) {
    suspend operator fun invoke(planId: String): Result<SavingPlan> {
        if (planId.isBlank()) {
            return Result.failure(IllegalArgumentException("Debes seleccionar un plan"))
        }

        return repository.getPlanById(planId)
    }
}