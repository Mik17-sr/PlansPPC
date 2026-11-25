package com.example.plansppc.presentation.detalle_plan

import com.example.plansppc.data.model.PlanDetail

data class DetallePlanUiState(
    val isLoading: Boolean = false,
    val planDetail: PlanDetail? = null,
    val errorMessage: String? = null
)
