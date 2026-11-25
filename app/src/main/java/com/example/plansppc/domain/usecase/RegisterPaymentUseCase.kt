package com.example.plansppc.domain.usecase

import com.example.plansppc.domain.repository.SavingRepository

class RegisterPaymentUseCase(private val repository: SavingRepository) {
    suspend operator fun invoke(planId: String, memberId: String, amount: Double): Result<Unit> {
        if (memberId.isBlank()) {
            return Result.failure(IllegalArgumentException("Debes seleccionar un miembro"))
        }

        if (amount <= 0) {
            return Result.failure(IllegalArgumentException("El monto debe ser mayor a $0"))
        }

        return repository.registerPayment(planId, memberId, amount)
    }
}