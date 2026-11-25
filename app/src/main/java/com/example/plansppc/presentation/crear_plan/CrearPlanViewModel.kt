package com.example.plansppc.presentation.crear_plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plansppc.domain.usecase.CrearPlanUseCase
import com.example.plansppc.utils.CurrencyUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CrearPlanViewModel(
    private val crearPlanUseCase: CrearPlanUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CrearPlanUiState())
    val uiState: StateFlow<CrearPlanUiState> = _uiState.asStateFlow()

    fun crearPlan(
        motivo: String,
        metaStr: String,
        mesesStr: String,
        miembrosStr: String,
        onSuccess: () -> Unit
    ) {
        if (motivo.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Por favor ingrese el motivo del plan")
            return
        }
        val cleanMeta = CurrencyUtils.cleanMoneyInput(metaStr)
        if (cleanMeta <= 0) {
            _uiState.value = _uiState.value.copy(errorMessage = "Por favor ingrese una meta válida")
            return
        }
        val meses = mesesStr.toIntOrNull()
        if (meses == null || meses <= 0) {
            _uiState.value = _uiState.value.copy(errorMessage = "Por favor ingrese una cantidad de meses válida")
            return
        }
        val miembrosList = miembrosStr.split(",", "\n").map { it.trim() }.filter { it.isNotEmpty() }
        if (miembrosList.isEmpty()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Por favor ingrese al menos un miembro")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val resultId = crearPlanUseCase(motivo, cleanMeta, meses, miembrosList)
                if (resultId != -1L) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        errorMessage = null
                    )
                    onSuccess()
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Error al guardar el plan en la API"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage
                )
            }
        }
    }
}
