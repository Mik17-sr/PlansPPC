package com.example.plansppc.presentation.detalle_plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plansppc.domain.usecase.GetDetallePlanUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetallePlanViewModel(
    private val getDetallePlanUseCase: GetDetallePlanUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetallePlanUiState())
    val uiState: StateFlow<DetallePlanUiState> = _uiState.asStateFlow()

    fun loadPlanDetail(planId: Long) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val detail = getDetallePlanUseCase(planId)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    planDetail = detail,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage
                )
            }
        }
    }
}
