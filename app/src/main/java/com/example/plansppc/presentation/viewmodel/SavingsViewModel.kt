package com.example.plansppc.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.plansppc.data.model.MiembroEntity
import com.example.plansppc.data.model.PlanDetail
import com.example.plansppc.data.model.PlanSummary
import com.example.plansppc.data.remote.RetrofitClient
import com.example.plansppc.data.repository.AhorroRepositoryImpl
import com.example.plansppc.domain.usecase.CrearPlanUseCase
import com.example.plansppc.domain.usecase.GetDetallePlanUseCase
import com.example.plansppc.domain.usecase.GetPlanesUseCase
import com.example.plansppc.domain.usecase.RegistrarPagoUseCase
import com.example.plansppc.utils.CurrencyUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class Screen {
    object Home : Screen()
    object CreatePlan : Screen()
    data class PlanDetailView(val planId: Long) : Screen()
    data class RegisterPayment(val planId: Long) : Screen()
}

class SavingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AhorroRepositoryImpl(RetrofitClient.api)
    private val crearPlanUseCase = CrearPlanUseCase(repository)
    private val getPlanesUseCase = GetPlanesUseCase(repository)
    private val getDetallePlanUseCase = GetDetallePlanUseCase(repository)
    private val registrarPagoUseCase = RegistrarPagoUseCase(repository)

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _planesList = MutableStateFlow<List<PlanSummary>>(emptyList())
    val planesList: StateFlow<List<PlanSummary>> = _planesList.asStateFlow()

    private val _selectedPlanDetail = MutableStateFlow<PlanDetail?>(null)
    val selectedPlanDetail: StateFlow<PlanDetail?> = _selectedPlanDetail.asStateFlow()

    private val _selectedPlanMiembros = MutableStateFlow<List<MiembroEntity>>(emptyList())
    val selectedPlanMiembros: StateFlow<List<MiembroEntity>> = _selectedPlanMiembros.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadPlanes()
    }

    fun loadPlanes() {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val planes = getPlanesUseCase()
                _planesList.value = planes
                if (repository.lastError != null) {
                    _errorMessage.value = repository.lastError
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = e.localizedMessage ?: "Error de conexión con la API"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateServerUrl(newUrl: String) {
        RetrofitClient.updateBaseUrl(newUrl)
        loadPlanes()
    }

    fun navigateTo(screen: Screen) {
        _errorMessage.value = null
        _currentScreen.value = screen
        when (screen) {
            is Screen.Home -> loadPlanes()
            is Screen.PlanDetailView -> loadPlanDetail(screen.planId)
            is Screen.RegisterPayment -> loadPlanMiembros(screen.planId)
            else -> {}
        }
    }

    fun loadPlanDetail(planId: Long) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val detail = getDetallePlanUseCase(planId)
                _selectedPlanDetail.value = detail
                if (repository.lastError != null) {
                    _errorMessage.value = repository.lastError
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = e.localizedMessage ?: "Error al cargar detalle"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadPlanMiembros(planId: Long) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val members = repository.getMiembrosForPlan(planId)
                _selectedPlanMiembros.value = members
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun crearPlan(
        motivo: String,
        metaStr: String,
        mesesStr: String,
        miembrosStr: String,
        onSuccess: () -> Unit
    ) {
        if (motivo.isBlank()) {
            _errorMessage.value = "Por favor ingrese el motivo del plan"
            return
        }
        val cleanMeta = CurrencyUtils.cleanMoneyInput(metaStr)
        if (cleanMeta <= 0) {
            _errorMessage.value = "Por favor ingrese una meta válida"
            return
        }
        val meses = mesesStr.toIntOrNull()
        if (meses == null || meses <= 0) {
            _errorMessage.value = "Por favor ingrese una cantidad de meses válida"
            return
        }
        val miembrosList = miembrosStr.split(",", "\n").map { it.trim() }.filter { it.isNotEmpty() }
        if (miembrosList.isEmpty()) {
            _errorMessage.value = "Por favor ingrese al menos un miembro"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val resultId = crearPlanUseCase(motivo, cleanMeta, meses, miembrosList)
                if (resultId != -1L) {
                    _errorMessage.value = null
                    loadPlanes()
                    onSuccess()
                } else {
                    _errorMessage.value = repository.lastError ?: "Error al guardar el plan en la API"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "Excepción al conectar con la API: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
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
            _errorMessage.value = "Por favor seleccione un miembro"
            return
        }
        val cleanMonto = CurrencyUtils.cleanMoneyInput(montoStr)
        if (cleanMonto <= 0) {
            _errorMessage.value = "Por favor ingrese un monto válido"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val success = registrarPagoUseCase(planId, miembroId, cleanMonto)
                if (success) {
                    _errorMessage.value = null
                    loadPlanDetail(planId)
                    loadPlanes()
                    onSuccess()
                } else {
                    _errorMessage.value = repository.lastError ?: "Error al registrar el pago en la API"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "Excepción al conectar con la API: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
