package com.example.plansppc.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.plansppc.ui.components.PrimaryButton
import com.example.plansppc.ui.theme.CardBorder
import com.example.plansppc.ui.theme.GreenPrimary
import com.example.plansppc.ui.theme.PlansPPCTheme
import com.example.plansppc.ui.theme.TextDark
import com.example.plansppc.ui.theme.TextMuted

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlanScreen(
    errorMessage: String?,
    onBack: () -> Unit,
    onCreatePlan: (motivo: String, meta: String, meses: String, miembros: String) -> Unit
) {
    var motivo by remember { mutableStateOf("") }
    var meta by remember { mutableStateOf("") }
    var meses by remember { mutableStateOf("") }
    var miembros by remember { mutableStateOf("") }

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
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Crear plan",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Field: Motivo
            Text(
                text = "Motivo",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = motivo,
                onValueChange = { motivo = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text("Ej: Ahorro para viaje familiar", color = TextMuted) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = CardBorder
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Field: Meta
            Text(
                text = "Meta",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = meta,
                onValueChange = { meta = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text("5.000.000", color = TextMuted) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = CardBorder
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Field: Meses
            Text(
                text = "Meses",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = meses,
                onValueChange = { meses = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text("12", color = TextMuted) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = CardBorder
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Field: Miembros
            Text(
                text = "Miembros",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = miembros,
                onValueChange = { miembros = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text("Jorge, Ana, Luis", color = TextMuted) },
                supportingText = { Text("Separa los nombres por comas", color = TextMuted, fontSize = 12.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenPrimary,
                    unfocusedBorderColor = CardBorder
                )
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

            Spacer(modifier = Modifier.height(40.dp))

            PrimaryButton(
                text = "Crear",
                onClick = {
                    onCreatePlan(motivo, meta, meses, miembros)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePlanScreenPreview() {
    PlansPPCTheme {
        CreatePlanScreen(
            errorMessage = null,
            onBack = {},
            onCreatePlan = { _, _, _, _ -> }
        )
    }
}
