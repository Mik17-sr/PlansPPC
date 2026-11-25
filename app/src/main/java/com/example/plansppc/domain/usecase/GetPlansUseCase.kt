package com.example.plansppc.domain.usecase

import com.example.plansppc.domain.model.SavingPlan
import com.example.plansppc.domain.repository.SavingRepository

class GetPlansUseCase(private val repository: SavingRepository) {
    suspend operator fun invoke(): Result<List<SavingPlan>> {
        return repository.getPlans()
    }
}