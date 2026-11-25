package com.example.plansppc.presentation.crear_plan

data class CrearPlanUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
