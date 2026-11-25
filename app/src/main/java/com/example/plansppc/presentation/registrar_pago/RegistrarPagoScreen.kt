package com.example.plansppc.presentation.registrar_pago

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plansppc.data.model.MiembroEntity
import com.example.plansppc.presentation.components.PrimaryButton
import com.example.plansppc.presentation.theme.CardBorder
import com.example.plansppc.presentation.theme.GreenPrimary
import com.example.plansppc.presentation.theme.PlansPPCTheme
import com.example.plansppc.presentation.theme.TextDark
import com.example.plansppc.presentation.theme.TextMuted
import com.example.plansppc.utils.CurrencyUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarPagoScreen(
    miembros: List<MiembroEntity>,
    errorMessage: String?,
    isLoading: Boolean = false,
    onBack: () -> Unit,
    onRegisterPayment: (miembroId: Long?, monto: String) -> Unit
) {
    var selectedMiembro by remember { mutableStateOf<MiembroEntity?>(miembros.firstOrNull()) }
    var expanded by remember { mutableStateOf(false) }
    var monto by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextDark
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
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
            }

            Text(
                text = "Registrar pago",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Miembro",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedMiembro?.nombre ?: "Seleccionar miembro",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true },
                    enabled = false,
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Desplegar",
                            tint = TextDark,
                            modifier = Modifier.clickable { expanded = true }
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = CardBorder,
                        disabledTextColor = TextDark,
                        disabledTrailingIconColor = TextDark
                    )
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth(0.85f)
                ) {
                    miembros.forEach { miembro ->
                        DropdownMenuItem(
                            text = { Text(text = miembro.nombre, fontSize = 16.sp, color = TextDark) },
                            onClick = {
                                selectedMiembro = miembro
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Monto",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = monto,
                onValueChange = { newValue ->
                    monto = CurrencyUtils.formatMoneyInput(newValue, monto)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                prefix = { Text("$  ", color = TextDark, fontWeight = FontWeight.Bold) },
                placeholder = { Text("Ingresa el monto", color = TextMuted) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = CardBorder
                ),
                singleLine = true
            )

            if (!errorMessage.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = "Registrar",
                isLoading = isLoading,
                onClick = {
                    onRegisterPayment(selectedMiembro?.id, monto)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrarPagoScreenPreview() {
    PlansPPCTheme {
        RegistrarPagoScreen(
            miembros = listOf(
                MiembroEntity(id = 1, planId = 1, nombre = "Jorge"),
                MiembroEntity(id = 2, planId = 1, nombre = "Ana"),
                MiembroEntity(id = 3, planId = 1, nombre = "Luis")
            ),
            errorMessage = null,
            isLoading = false,
            onBack = {},
            onRegisterPayment = { _, _ -> }
        )
    }
}
