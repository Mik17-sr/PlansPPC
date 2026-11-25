package com.example.plansppc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plansppc.ui.theme.CardBorder
import com.example.plansppc.ui.theme.GreenPrimary
import com.example.plansppc.ui.theme.GreenSecondary
import com.example.plansppc.ui.theme.PlansPPCTheme
import com.example.plansppc.ui.theme.TextDark
import com.example.plansppc.ui.theme.TextMuted

data class BarChartEntry(val label: String, val valueFraction: Float)

// 1. Gráfico de Barras de Ahorro Mensual
@Composable
fun SimpleBarChart(
    title: String,
    entries: List<BarChartEntry>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                entries.forEach { entry ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.weight(1f)
                    ) {
                        val barHeight = (100 * entry.valueFraction.coerceIn(0.1f, 1f)).dp
                        val isHighest = entry.valueFraction >= 0.9f
                        Box(
                            modifier = Modifier
                                .width(18.dp)
                                .height(barHeight)
                                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                .background(if (isHighest) GreenPrimary else GreenSecondary)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = entry.label, fontSize = 12.sp, color = TextMuted, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}

// 2. Línea de Tiempo de Pasos de Meta (Step Timeline)
data class TimelineStep(val title: String, val isCompleted: Boolean)

@Composable
fun TimelineStepIndicator(
    steps: List<TimelineStep>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        steps.forEachIndexed { index, step ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(if (step.isCompleted) GreenPrimary else GreenSecondary),
                    contentAlignment = Alignment.Center
                ) {
                    if (step.isCompleted) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    } else {
                        Text(text = "${index + 1}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = step.title,
                    fontSize = 14.sp,
                    fontWeight = if (step.isCompleted) FontWeight.Bold else FontWeight.Normal,
                    color = if (step.isCompleted) TextDark else TextMuted
                )
            }
            if (index < steps.size - 1) {
                Box(
                    modifier = Modifier
                        .padding(start = 13.dp, top = 2.dp, bottom = 2.dp)
                        .width(2.dp)
                        .height(20.dp)
                        .background(if (step.isCompleted) GreenPrimary else CardBorder)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppChartsPreview() {
    PlansPPCTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SimpleBarChart(
                title = "Recaudo Mensual (2026)",
                entries = listOf(
                    BarChartEntry("Ene", 0.4f),
                    BarChartEntry("Feb", 0.6f),
                    BarChartEntry("Mar", 0.3f),
                    BarChartEntry("Abr", 0.8f),
                    BarChartEntry("May", 1.0f),
                    BarChartEntry("Jun", 0.7f)
                )
            )

            TimelineStepIndicator(
                steps = listOf(
                    TimelineStep("Plan de Ahorro Creado", true),
                    TimelineStep("Primer Abono del Grupo", true),
                    TimelineStep("50% de la Meta Alcanzada", false),
                    TimelineStep("Meta Final Completada", false)
                )
            )
        }
    }
}
