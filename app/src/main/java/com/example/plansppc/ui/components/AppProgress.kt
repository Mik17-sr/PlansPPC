package com.example.plansppc.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plansppc.ui.theme.CardBorder
import com.example.plansppc.ui.theme.GreenPrimary
import com.example.plansppc.ui.theme.PlansPPCTheme
import com.example.plansppc.ui.theme.TextDark
import com.example.plansppc.ui.theme.TextMuted
import com.example.plansppc.utils.CurrencyUtils

@Composable
fun ProgressSection(
    currentAmount: Double,
    targetAmount: Double,
    modifier: Modifier = Modifier,
    label: String = "Progreso del Ahorro"
) {
    val progress = if (targetAmount > 0) (currentAmount / targetAmount).coerceIn(0.0, 1.0).toFloat() else 0f
    val percentage = (progress * 100).toInt()

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextMuted
            )
            Text(
                text = "$percentage%",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = GreenPrimary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = GreenPrimary,
            trackColor = CardBorder,
            drawStopIndicator = {}
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Recaudado: $ ${CurrencyUtils.formatAmount(currentAmount)}",
                fontSize = 12.sp,
                color = TextDark,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Meta: $ ${CurrencyUtils.formatAmount(targetAmount)}",
                fontSize = 12.sp,
                color = TextMuted
            )
        }
    }
}

@Composable
fun CircularProgressRing(
    progress: Float,
    modifier: Modifier = Modifier,
    size: Int = 80,
    color: Color = GreenPrimary
) {
    val percentage = (progress.coerceIn(0f, 1f) * 100).toInt()

    Box(
        modifier = modifier.size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { progress.coerceIn(0f, 1f) },
            modifier = Modifier.size(size.dp),
            color = color,
            strokeWidth = 6.dp,
            trackColor = CardBorder
        )
        Text(
            text = "$percentage%",
            fontSize = (size / 5).sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppProgressPreview() {
    PlansPPCTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProgressSection(
                currentAmount = 1500000.0,
                targetAmount = 5000000.0
            )

            CircularProgressRing(progress = 0.65f)
        }
    }
}
