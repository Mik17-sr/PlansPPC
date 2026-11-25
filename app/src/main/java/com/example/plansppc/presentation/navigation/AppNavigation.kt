package com.example.plansppc.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.plansppc.presentation.crear_plan.CrearPlanScreen
import com.example.plansppc.presentation.detalle_plan.DetallePlanScreen
import com.example.plansppc.presentation.home.HomeScreen
import com.example.plansppc.presentation.registrar_pago.RegistrarPagoScreen
import com.example.plansppc.presentation.viewmodel.SavingsViewModel
import com.example.plansppc.presentation.viewmodel.Screen

@Composable
fun AppNavigation(
    viewModel: SavingsViewModel
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val planesList by viewModel.planesList.collectAsState()
    val selectedPlanDetail by viewModel.selectedPlanDetail.collectAsState()
    val selectedPlanMiembros by viewModel.selectedPlanMiembros.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    when (val screen = currentScreen) {
        is Screen.Home -> {
            HomeScreen(
                planes = planesList,
                isLoading = isLoading,
                errorMessage = errorMessage,
                onNavigateToCreatePlan = { viewModel.navigateTo(Screen.CreatePlan) },
                onSelectPlan = { planId -> viewModel.navigateTo(Screen.PlanDetailView(planId)) },
                onUpdateServerUrl = { newUrl -> viewModel.updateServerUrl(newUrl) }
            )
        }
        is Screen.CreatePlan -> {
            CrearPlanScreen(
                errorMessage = errorMessage,
                isLoading = isLoading,
                onBack = { viewModel.navigateTo(Screen.Home) },
                onCreatePlan = { motivo, meta, meses, miembros ->
                    viewModel.crearPlan(motivo, meta, meses, miembros) {
                        viewModel.navigateTo(Screen.Home)
                    }
                }
            )
        }
        is Screen.PlanDetailView -> {
            DetallePlanScreen(
                planDetail = selectedPlanDetail,
                isLoading = isLoading,
                onBack = { viewModel.navigateTo(Screen.Home) },
                onNavigateToRegisterPayment = { planId ->
                    viewModel.navigateTo(Screen.RegisterPayment(planId))
                }
            )
        }
        is Screen.RegisterPayment -> {
            RegistrarPagoScreen(
                miembros = selectedPlanMiembros,
                errorMessage = errorMessage,
                isLoading = isLoading,
                onBack = { viewModel.navigateTo(Screen.PlanDetailView(screen.planId)) },
                onRegisterPayment = { miembroId, monto ->
                    viewModel.registrarPago(screen.planId, miembroId, monto) {
                        viewModel.navigateTo(Screen.PlanDetailView(screen.planId))
                    }
                }
            )
        }
    }
}
