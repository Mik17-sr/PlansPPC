package com.example.plansppc.presentation.registrar_pago

import com.example.plansppc.data.model.MiembroEntity

data class RegistrarPagoUiState(
    val isLoading: Boolean = false,
    val miembros: List<MiembroEntity> = emptyList(),
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
