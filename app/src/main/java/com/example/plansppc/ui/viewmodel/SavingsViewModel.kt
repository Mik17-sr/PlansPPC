package com.example.plansppc.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.plansppc.data.db.DatabaseHelper
import com.example.plansppc.data.model.MiembroEntity
import com.example.plansppc.data.model.PlanDetail
import com.example.plansppc.data.model.PlanSummary
import com.example.plansppc.data.repository.SavingsRepository
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

    private val dbHelper = DatabaseHelper(application)
    private val repository = SavingsRepository(dbHelper)

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _planesList = MutableStateFlow<List<PlanSummary>>(emptyList())
    val planesList: StateFlow<List<PlanSummary>> = _planesList.asStateFlow()

    private val _selectedPlanDetail = MutableStateFlow<PlanDetail?>(null)
    val selectedPlanDetail: StateFlow<PlanDetail?> = _selectedPlanDetail.asStateFlow()

    private val _selectedPlanMiembros = MutableStateFlow<List<MiembroEntity>>(emptyList())
    val selectedPlanMiembros: StateFlow<List<MiembroEntity>> = _selectedPlanMiembros.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadDataAndSeedIfEmpty()
    }

    private fun loadDataAndSeedIfEmpty() {
        viewModelScope.launch {
            val planes = repository.getPlanesSummary()
            if (planes.isEmpty()) {
                // Seed initial data matching mockup requirement
                val planId = repository.crearPlan(
                    motivo = "Ahorro para viaje familiar",
                    meta = 5000000.0,
                    meses = 12,
                    miembros = listOf("Jorge", "Ana", "Luis")
                )
                if (planId != -1L) {
                    val miembros = repository.getMiembrosForPlan(planId)
                    val jorge = miembros.find { it.nombre == "Jorge" }
                    val ana = miembros.find { it.nombre == "Ana" }
                    val luis = miembros.find { it.nombre == "Luis" }

                    jorge?.let { repository.registrarPago(planId, it.id, 500000.0) }
                    ana?.let { repository.registrarPago(planId, it.id, 500000.0) }
                    luis?.let { repository.registrarPago(planId, it.id, 500000.0) }
                }
                loadPlanes()
            } else {
                _planesList.value = planes
            }
        }
    }

    fun loadPlanes() {
        viewModelScope.launch {
            _planesList.value = repository.getPlanesSummary()
        }
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
        viewModelScope.launch {
            _selectedPlanDetail.value = repository.getPlanDetail(planId)
        }
    }

    private fun loadPlanMiembros(planId: Long) {
        viewModelScope.launch {
            _selectedPlanMiembros.value = repository.getMiembrosForPlan(planId)
        }
    }

    fun crearPlan(motivo: String, metaStr: String, mesesStr: String, miembrosStr: String, onSuccess: () -> Unit) {
        if (motivo.isBlank()) {
            _errorMessage.value = "Por favor ingrese el motivo del plan"
            return
        }
        val meta = metaStr.replace(".", "").toDoubleOrNull()
        if (meta == null || meta <= 0) {
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

        viewModelScope.launch {
            val resultId = repository.crearPlan(motivo, meta, meses, miembrosList)
            if (resultId != -1L) {
                _errorMessage.value = null
                loadPlanes()
                onSuccess()
            } else {
                _errorMessage.value = "Error al guardar el plan en SQLite"
            }
        }
    }

    fun registrarPago(planId: Long, miembroId: Long?, montoStr: String, onSuccess: () -> Unit) {
        if (miembroId == null || miembroId <= 0) {
            _errorMessage.value = "Por favor seleccione un miembro"
            return
        }
        val monto = montoStr.replace(".", "").toDoubleOrNull()
        if (monto == null || monto <= 0) {
            _errorMessage.value = "Por favor ingrese un monto válido"
            return
        }

        viewModelScope.launch {
            val success = repository.registrarPago(planId, miembroId, monto)
            if (success) {
                _errorMessage.value = null
                loadPlanDetail(planId)
                onSuccess()
            } else {
                _errorMessage.value = "Error al registrar el pago en SQLite"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
