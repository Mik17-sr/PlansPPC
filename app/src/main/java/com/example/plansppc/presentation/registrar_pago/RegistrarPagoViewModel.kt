package com.example.plansppc.presentation.registrar_pago

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plansppc.domain.repository.AhorroRepository
import com.example.plansppc.domain.usecase.RegistrarPagoUseCase
import com.example.plansppc.utils.CurrencyUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrarPagoViewModel(
    private val repository: AhorroRepository,
    private val registrarPagoUseCase: RegistrarPagoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrarPagoUiState())
    val uiState: StateFlow<RegistrarPagoUiState> = _uiState.asStateFlow()

    fun loadMiembros(planId: Long) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val members = repository.getMiembrosForPlan(planId)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    miembros = members
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage
                )
            }
        }
    }

    fun registrarPago(
        planId: Long,
        miembroId: Long?,
        montoStr: String,
        onSuccess: () -> Unit
    ) {
        if (miembroId == null || miembroId <= 0) {
            _uiState.value = _uiState.value.copy(errorMessage = "Por favor seleccione un miembro")
            return
        }
        val cleanMonto = CurrencyUtils.cleanMoneyInput(montoStr)
        if (cleanMonto <= 0) {
            _uiState.value = _uiState.value.copy(errorMessage = "Por favor ingrese un monto válido")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val success = registrarPagoUseCase(planId, miembroId, cleanMonto)
                if (success) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        errorMessage = null
                    )
                    onSuccess()
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Error al registrar el pago en la API"
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
