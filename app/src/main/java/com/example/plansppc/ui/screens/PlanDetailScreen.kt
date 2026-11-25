package com.example.plansppc.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plansppc.data.model.MiembroSummary
import com.example.plansppc.data.model.PlanDetail
import com.example.plansppc.ui.components.PrimaryButton
import com.example.plansppc.ui.theme.CardBorder
import com.example.plansppc.ui.theme.GreenPrimary
import com.example.plansppc.ui.theme.GreenSecondary
import com.example.plansppc.ui.theme.PlansPPCTheme
import com.example.plansppc.ui.theme.TextDark
import com.example.plansppc.ui.theme.TextMuted
import com.example.plansppc.utils.CurrencyUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanDetailScreen(
    planDetail: PlanDetail?,
    onBack: () -> Unit,
    onNavigateToRegisterPayment: (planId: Long) -> Unit
) {
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
        },
        bottomBar = {
            if (planDetail != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    PrimaryButton(
                        text = "Registrar pago",
                        onClick = { onNavigateToRegisterPayment(planDetail.id) }
                    )
                }
            }
        }
    ) { innerPadding ->
        if (planDetail == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = GreenPrimary)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = planDetail.motivo,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    lineHeight = 34.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "${planDetail.meses} meses",
                    fontSize = 16.sp,
                    color = TextMuted,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = CurrencyUtils.formatAmount(planDetail.meta),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Ahorrado actual: $ ${CurrencyUtils.formatAmount(planDetail.totalAhorrado)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = GreenPrimary
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Miembros",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(planDetail.miembros) { miembro ->
                        MemberItemCard(miembro = miembro)
                    }
                }
            }
        }
    }
}

@Composable
fun MemberItemCard(miembro: MiembroSummary) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(GreenSecondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = miembro.nombre,
                    tint = GreenPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = miembro.nombre,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
                if (miembro.totalPagado > 0) {
                    Text(
                        text = "Abonado: $ ${CurrencyUtils.formatAmount(miembro.totalPagado)}",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlanDetailScreenPreview() {
    PlansPPCTheme {
        PlanDetailScreen(
            planDetail = PlanDetail(
                id = 1,
                motivo = "Ahorro para viaje familiar",
                meta = 5000000.0,
                meses = 12,
                totalAhorrado = 1500000.0,
                miembros = listOf(
                    MiembroSummary(id = 1, nombre = "Jorge", totalPagado = 500000.0),
                    MiembroSummary(id = 2, nombre = "Ana", totalPagado = 500000.0),
                    MiembroSummary(id = 3, nombre = "Luis", totalPagado = 500000.0)
                )
            ),
            onBack = {},
            onNavigateToRegisterPayment = {}
        )
    }
}
