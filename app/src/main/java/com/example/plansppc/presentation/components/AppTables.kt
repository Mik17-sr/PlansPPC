package com.example.plansppc.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plansppc.presentation.theme.CardBorder
import com.example.plansppc.presentation.theme.GreenPrimary
import com.example.plansppc.presentation.theme.GreenSecondary
import com.example.plansppc.presentation.theme.PlansPPCTheme
import com.example.plansppc.presentation.theme.TextDark
import com.example.plansppc.presentation.theme.TextMuted

data class TableColumn(
    val title: String,
    val weight: Float = 1f,
    val alignment: TextAlign = TextAlign.Start
)

@Composable
fun DataTable(
    columns: List<TableColumn>,
    rows: List<List<String>>,
    modifier: Modifier = Modifier,
    zebraStriping: Boolean = true
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(GreenSecondary)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                columns.forEach { column ->
                    Text(
                        text = column.title,
                        modifier = Modifier.weight(column.weight),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = GreenPrimary,
                        textAlign = column.alignment
                    )
                }
            }

            HorizontalDivider(color = CardBorder, thickness = 1.dp)

            rows.forEachIndexed { index, rowData ->
                val backgroundColor = if (zebraStriping && index % 2 == 1) Color(0xFFF8FAF8) else Color.White
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    rowData.forEachIndexed { cellIndex, cellText ->
                        val column = columns.getOrNull(cellIndex) ?: TableColumn("", 1f)
                        Text(
                            text = cellText,
                            modifier = Modifier.weight(column.weight),
                            fontSize = 14.sp,
                            fontWeight = if (cellIndex == 0) FontWeight.SemiBold else FontWeight.Normal,
                            color = TextDark,
                            textAlign = column.alignment
                        )
                    }
                }
                if (index < rows.size - 1) {
                    HorizontalDivider(color = CardBorder.copy(alpha = 0.5f), thickness = 0.5.dp)
                }
            }
        }
    }
}

@Composable
fun TransactionRow(
    memberName: String,
    date: String,
    amount: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(GreenSecondary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = memberName,
                tint = GreenPrimary,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = memberName, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
            Text(text = date, fontSize = 12.sp, color = TextMuted)
        }
        Text(
            text = "+ $amount",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = GreenPrimary
        )
    }
}

@Composable
fun KeyValueRow(
    key: String,
    value: String,
    modifier: Modifier = Modifier,
    isHighlighted: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = key,
            fontSize = 14.sp,
            color = TextMuted,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = if (isHighlighted) 16.sp else 14.sp,
            color = if (isHighlighted) GreenPrimary else TextDark,
            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppTablesPreview() {
    PlansPPCTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Tabla de Pagos / Miembros", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            DataTable(
                columns = listOf(
                    TableColumn("Miembro", 1.5f),
                    TableColumn("Abono", 1f, TextAlign.End),
                    TableColumn("Estado", 1f, TextAlign.End)
                ),
                rows = listOf(
                    listOf("Jorge", "$ 500.000", "Al día"),
                    listOf("Ana", "$ 500.000", "Al día"),
                    listOf("Luis", "$ 250.000", "Pendiente")
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            TransactionRow(memberName = "Jorge", date = "Hoy, 10:30 AM", amount = "$ 500.000")
            TransactionRow(memberName = "Ana", date = "Ayer, 4:15 PM", amount = "$ 500.000")

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                KeyValueRow(key = "Meta Total", value = "$ 5.000.000")
                KeyValueRow(key = "Duración", value = "12 meses")
                KeyValueRow(key = "Total Recaudado", value = "$ 1.250.000", isHighlighted = true)
            }
        }
    }
}
