package com.example.plansppc.presentation.home

import com.example.plansppc.data.model.PlanSummary

data class HomeUiState(
    val isLoading: Boolean = false,
    val planes: List<PlanSummary> = emptyList(),
    val errorMessage: String? = null
)
