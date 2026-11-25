package com.example.plansppc.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plansppc.data.model.PlanSummary
import com.example.plansppc.data.remote.RetrofitClient
import com.example.plansppc.presentation.components.AppDropdownMenu
import com.example.plansppc.presentation.components.AppTextButton
import com.example.plansppc.presentation.components.CardSkeletonLoader
import com.example.plansppc.presentation.components.DataTable
import com.example.plansppc.presentation.components.EmptyStateCard
import com.example.plansppc.presentation.components.FilterChipItem
import com.example.plansppc.presentation.components.FormCardSection
import com.example.plansppc.presentation.components.FormCheckboxRow
import com.example.plansppc.presentation.components.FormDatePickerField
import com.example.plansppc.presentation.components.FormFileUploadField
import com.example.plansppc.presentation.components.FormMultiSelectChips
import com.example.plansppc.presentation.components.FormPasswordInputField
import com.example.plansppc.presentation.components.FormRadioGroup
import com.example.plansppc.presentation.components.FormSlider
import com.example.plansppc.presentation.components.FormSwitchRow
import com.example.plansppc.presentation.components.FormTimePickerField
import com.example.plansppc.presentation.components.GradientBannerCard
import com.example.plansppc.presentation.components.IconButtonWithBadge
import com.example.plansppc.presentation.components.KeyValueRow
import com.example.plansppc.presentation.components.LabeledInputField
import com.example.plansppc.presentation.components.MemberAvatarTile
import com.example.plansppc.presentation.components.MessageBanner
import com.example.plansppc.presentation.components.MetricCard
import com.example.plansppc.presentation.components.PrimaryButton
import com.example.plansppc.presentation.components.ProgressSection
import com.example.plansppc.presentation.components.QuickActionTile
import com.example.plansppc.presentation.components.SearchInputField
import com.example.plansppc.presentation.components.SecondaryButton
import com.example.plansppc.presentation.components.StatusBadge
import com.example.plansppc.presentation.components.TableColumn
import com.example.plansppc.presentation.components.TransactionRow
import com.example.plansppc.presentation.theme.CardBorder
import com.example.plansppc.presentation.theme.GreenPrimary
import com.example.plansppc.presentation.theme.PlansPPCTheme
import com.example.plansppc.presentation.theme.TextDark
import com.example.plansppc.presentation.theme.TextMuted
import com.example.plansppc.utils.CurrencyUtils

@Composable
fun HomeScreen(
    planes: List<PlanSummary>,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onNavigateToCreatePlan: () -> Unit,
    onSelectPlan: (Long) -> Unit,
    onUpdateServerUrl: (String) -> Unit = {}
) {
    var showServerDialog by remember { mutableStateOf(false) }
    var serverUrlInput by remember { mutableStateOf(RetrofitClient.baseUrl) }

    if (showServerDialog) {
        AlertDialog(
            onDismissRequest = { showServerDialog = false },
            title = { Text("Configurar Servidor API", fontWeight = FontWeight.Bold, color = TextDark) },
            text = {
                Column {
                    Text(
                        text = "Ingresa la URL o IP de tu servidor backend:",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = serverUrlInput,
                        onValueChange = { serverUrlInput = it },
                        singleLine = true,
                        placeholder = { Text("http://10.0.2.2:3000/") },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            confirmButton = {
                AppTextButton(
                    text = "Guardar",
                    onClick = {
                        onUpdateServerUrl(serverUrlInput)
                        showServerDialog = false
                    }
                )
            },
            dismissButton = {
                AppTextButton(
                    text = "Cancelar",
                    onClick = { showServerDialog = false },
                    color = TextMuted
                )
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp),
                color = GreenPrimary,
                trackColor = Color.Transparent
            )
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            Spacer(modifier = Modifier.height(11.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Plan Familiar\nde Ahorro",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                lineHeight = 34.sp
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { showServerDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Dns,
                        contentDescription = "Configurar Servidor",
                        tint = TextDark
                    )
                }
                IconButtonWithBadge(
                    icon = Icons.Default.Notifications,
                    badgeCount = 3,
                    onClick = {}
                )
            }
        }

        if (!errorMessage.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            MessageBanner(message = errorMessage, isError = true)
        }

        Spacer(modifier = Modifier.height(20.dp))

        HomeScreenPlansView(
            planes = planes,
            isLoading = isLoading,
            onNavigateToCreatePlan = onNavigateToCreatePlan,
            onSelectPlan = onSelectPlan
        )
    }
}

@Composable
fun HomeScreenPlansView(
    planes: List<PlanSummary>,
    isLoading: Boolean = false,
    onNavigateToCreatePlan: () -> Unit,
    onSelectPlan: (Long) -> Unit
) {
    val totalAhorradoGeneral = planes.sumOf { it.totalAhorrado }
    val totalMetaGeneral = planes.sumOf { it.meta }

    Column(modifier = Modifier.fillMaxSize()) {
        if (planes.isNotEmpty()) {
            MetricCard(
                title = "Ahorro Total Familiar",
                value = "$ ${CurrencyUtils.formatAmount(totalAhorradoGeneral)}",
                subtitle = "Meta global: $ ${CurrencyUtils.formatAmount(totalMetaGeneral)}",
                icon = Icons.Default.AccountBalanceWallet
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        PrimaryButton(
            text = "Crear plan",
            onClick = onNavigateToCreatePlan,
            icon = Icons.Default.Add
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Planes de ahorro",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading && planes.isEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                CardSkeletonLoader()
                CardSkeletonLoader()
                CardSkeletonLoader()
            }
        } else if (planes.isEmpty()) {
            EmptyStateCard(
                title = "No tienes planes registrados",
                message = "Crea un plan de ahorro familiar para comenzar a registrar abonos.",
                icon = Icons.Default.Savings,
                actionButton = {
                    PrimaryButton(
                        text = "Crear primer plan",
                        onClick = onNavigateToCreatePlan,
                        icon = Icons.Default.Add
                    )
                }
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(planes) { plan ->
                    PlanCardItem(
                        plan = plan,
                        onClick = { onSelectPlan(plan.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun UICatalogView() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilterChip by remember { mutableStateOf("Todos") }
    var inputTest by remember { mutableStateOf("") }
    var selectedMemberTest by remember { mutableStateOf("Jorge") }

    var formChecked by remember { mutableStateOf(true) }
    var formRadioSelected by remember { mutableStateOf("Mensual") }
    var formSwitchState by remember { mutableStateOf(true) }
    var formSliderVal by remember { mutableFloatStateOf(12f) }
    var formMultiChips by remember { mutableStateOf(setOf("Viajes", "Educación")) }
    var formPassword by remember { mutableStateOf("12345678") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        CatalogSectionHeader("1. Búsqueda y Formularios Básicos")

        SearchInputField(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            placeholder = "Buscar componente..."
        )

        LabeledInputField(
            label = "Campo de Texto Reutilizable",
            value = inputTest,
            onValueChange = { inputTest = it },
            placeholder = "Escribe algo aquí..."
        )

        AppDropdownMenu(
            label = "Selector Desplegable",
            items = listOf("Jorge", "Ana", "Luis", "Carlos"),
            selectedItem = selectedMemberTest,
            itemLabel = { it },
            onItemSelected = { selectedMemberTest = it }
        )

        HorizontalDivider(color = CardBorder)

        CatalogSectionHeader("2. Controles de Formulario Avanzados")

        FormCardSection(
            title = "Ajustes y Parámetros del Plan",
            icon = Icons.Default.EditNote
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                FormCheckboxRow(
                    label = "Aceptar notificaciones del grupo",
                    checked = formChecked,
                    onCheckedChange = { formChecked = it },
                    description = "Envía recordatorios automáticos de cuota mensual"
                )

                FormRadioGroup(
                    title = "Frecuencia de Recaudo",
                    options = listOf("Semanal", "Quincenal", "Mensual"),
                    selectedOption = formRadioSelected,
                    onOptionSelected = { formRadioSelected = it }
                )

                FormSwitchRow(
                    title = "Alertas de Vencimiento",
                    subtitle = "Notificar por correo cuando la meta esté cerca",
                    checked = formSwitchState,
                    onCheckedChange = { formSwitchState = it }
                )

                FormSlider(
                    label = "Plazo Estimado",
                    value = formSliderVal,
                    valueRange = 1f..36f,
                    onValueChange = { formSliderVal = it },
                    valueFormatter = { "${it.toInt()} meses" }
                )

                FormMultiSelectChips(
                    title = "Categorías de Ahorro",
                    options = listOf("Viajes", "Educación", "Hogar", "Emergencias"),
                    selectedOptions = formMultiChips,
                    onOptionToggled = { chip ->
                        formMultiChips = if (formMultiChips.contains(chip)) formMultiChips - chip else formMultiChips + chip
                    }
                )

                FormDatePickerField(label = "Fecha de Inicio del Plan", selectedDate = "24/11/2026", onClick = {})
                FormTimePickerField(label = "Hora de Recordatorio", selectedTime = "09:00 AM", onClick = {})
                FormFileUploadField(label = "Adjuntar Comprobante", fileName = "Abono_Familia.pdf", onUploadClick = {})
                FormPasswordInputField(label = "Clave de Autorización", value = formPassword, onValueChange = { formPassword = it })
            }
        }

        HorizontalDivider(color = CardBorder)

        CatalogSectionHeader("3. Banners Promocionales y Gradientes")

        GradientBannerCard(
            title = "¡Ahorro Familiar 2026!",
            subtitle = "Alcanza tus metas financieras junto a tus seres queridos.",
            actionText = "Explorar metas",
            onActionClick = {}
        )

        HorizontalDivider(color = CardBorder)

        CatalogSectionHeader("4. Botones y Chips de Selección")

        PrimaryButton(text = "Botón Primario", onClick = {}, icon = Icons.Default.Add)
        SecondaryButton(text = "Botón Secundario", onClick = {})

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Todos", "Familia", "Viajes", "Educación").forEach { chip ->
                FilterChipItem(
                    label = chip,
                    isSelected = selectedFilterChip == chip,
                    onSelect = { selectedFilterChip = chip }
                )
            }
        }

        HorizontalDivider(color = CardBorder)

        CatalogSectionHeader("5. Tarjetas Visuales y Acciones Rápida")

        MetricCard(
            title = "Total Recaudado Global",
            value = "$ 2.500.000",
            subtitle = "Progreso: 50% completado",
            icon = Icons.Default.AccountBalanceWallet
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            QuickActionTile(
                title = "Nuevo Plan",
                icon = Icons.Default.Savings,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            QuickActionTile(
                title = "Registrar Pago",
                icon = Icons.Default.Person,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }

        Text(text = "Miembros Destacados", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextMuted)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MemberAvatarTile(name = "Jorge", amount = "$ 500.000")
            MemberAvatarTile(name = "Ana", amount = "$ 500.000")
        }

        HorizontalDivider(color = CardBorder)

        CatalogSectionHeader("6. Badges de Estado y Progreso")

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatusBadge(text = "Completado")
            StatusBadge(text = "En Progreso", backgroundColor = Color(0xFFFEF3C7), contentColor = Color(0xFFD97706))
            StatusBadge(text = "Pendiente", backgroundColor = Color(0xFFFEE2E2), contentColor = Color(0xFFDC2626))
        }

        ProgressSection(
            currentAmount = 1500000.0,
            targetAmount = 5000000.0,
            label = "Métrica de Avance Ahorro"
        )

        HorizontalDivider(color = CardBorder)

        CatalogSectionHeader("7. Tablas e Historial de Transacciones")

        TransactionRow(memberName = "Jorge", date = "Hoy, 10:30 AM", amount = "500.000")
        TransactionRow(memberName = "Ana", date = "Ayer, 4:15 PM", amount = "500.000")

        DataTable(
            columns = listOf(
                TableColumn("Miembro", 1.5f),
                TableColumn("Abono", 1f, TextAlign.End),
                TableColumn("Estado", 1f, TextAlign.End)
            ),
            rows = listOf(
                listOf("Jorge", "$ 500.000", "Al día"),
                listOf("Ana", "$ 500.000", "Al día"),
                listOf("Luis", "$ 500.000", "Al día")
            )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            KeyValueRow(key = "Frecuencia de Abono", value = "Mensual")
            KeyValueRow(key = "Meta General", value = "$ 5.000.000", isHighlighted = true)
        }

        HorizontalDivider(color = CardBorder)

        CatalogSectionHeader("8. Notificaciones y Banners")

        MessageBanner(message = "¡Operación completada exitosamente!")
        MessageBanner(message = "Atención: Verifique el monto ingresado", isError = true)
    }
}

@Composable
fun CatalogSectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = TextDark
    )
}

@Composable
fun PlanCardItem(
    plan: PlanSummary,
    onClick: () -> Unit
) {
    val isCompleted = plan.totalAhorrado >= plan.meta && plan.meta > 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = plan.motivo,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = "${plan.meses} meses • ${plan.cantidadMiembros} miembros",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                }

                if (isCompleted) {
                    StatusBadge(text = "Completado")
                } else {
                    StatusBadge(
                        text = "En Progreso",
                        backgroundColor = Color(0xFFFEF3C7),
                        contentColor = Color(0xFFD97706)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$ ${CurrencyUtils.formatAmount(plan.meta)}",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProgressSection(
                currentAmount = plan.totalAhorrado,
                targetAmount = plan.meta
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PlansPPCTheme {
        HomeScreen(
            planes = listOf(
                PlanSummary(
                    id = 1,
                    motivo = "Ahorro para viaje familiar",
                    meta = 5000000.0,
                    meses = 12,
                    totalAhorrado = 1500000.0,
                    cantidadMiembros = 3
                )
            ),
            isLoading = false,
            errorMessage = null,
            onNavigateToCreatePlan = {},
            onSelectPlan = {}
        )
    }
}
