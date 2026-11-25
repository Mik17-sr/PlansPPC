package com.example.plansppc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.plansppc.ui.screens.CreatePlanScreen
import com.example.plansppc.ui.screens.HomeScreen
import com.example.plansppc.ui.screens.PlanDetailScreen
import com.example.plansppc.ui.screens.RegisterPaymentScreen
import com.example.plansppc.ui.theme.PlansPPCTheme
import com.example.plansppc.ui.viewmodel.SavingsViewModel
import com.example.plansppc.ui.viewmodel.Screen

class MainActivity : ComponentActivity() {

    private val viewModel: SavingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlansPPCTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: SavingsViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val planesList by viewModel.planesList.collectAsState()
    val planDetail by viewModel.selectedPlanDetail.collectAsState()
    val planMiembros by viewModel.selectedPlanMiembros.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    when (val screen = currentScreen) {
        is Screen.Home -> {
            HomeScreen(
                planes = planesList,
                onNavigateToCreatePlan = {
                    viewModel.navigateTo(Screen.CreatePlan)
                },
                onSelectPlan = { planId ->
                    viewModel.navigateTo(Screen.PlanDetailView(planId))
                }
            )
        }

        is Screen.CreatePlan -> {
            BackHandler {
                viewModel.navigateTo(Screen.Home)
            }
            CreatePlanScreen(
                errorMessage = errorMessage,
                onBack = {
                    viewModel.navigateTo(Screen.Home)
                },
                onCreatePlan = { motivo, meta, meses, miembros ->
                    viewModel.crearPlan(motivo, meta, meses, miembros) {
                        viewModel.navigateTo(Screen.Home)
                    }
                }
            )
        }

        is Screen.PlanDetailView -> {
            BackHandler {
                viewModel.navigateTo(Screen.Home)
            }
            PlanDetailScreen(
                planDetail = planDetail,
                onBack = {
                    viewModel.navigateTo(Screen.Home)
                },
                onNavigateToRegisterPayment = { planId ->
                    viewModel.navigateTo(Screen.RegisterPayment(planId))
                }
            )
        }

        is Screen.RegisterPayment -> {
            BackHandler {
                viewModel.navigateTo(Screen.PlanDetailView(screen.planId))
            }
            RegisterPaymentScreen(
                miembros = planMiembros,
                errorMessage = errorMessage,
                onBack = {
                    viewModel.navigateTo(Screen.PlanDetailView(screen.planId))
                },
                onRegisterPayment = { miembroId, monto ->
                    viewModel.registrarPago(screen.planId, miembroId, monto) {
                        viewModel.navigateTo(Screen.PlanDetailView(screen.planId))
                    }
                }
            )
        }
    }
}
