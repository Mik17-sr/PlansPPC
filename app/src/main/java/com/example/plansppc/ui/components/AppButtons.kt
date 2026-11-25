package com.example.plansppc.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GreenPrimary,
            contentColor = Color.White,
            disabledContainerColor = CardBorder,
            disabledContentColor = Color.Gray
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.5.dp
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        enabled = enabled,
        shape = RoundedCornerShape(26.dp),
        border = BorderStroke(1.5.dp, if (enabled) GreenPrimary else CardBorder),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = GreenPrimary
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = GreenPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = GreenPrimary
            )
        }
    }
}

@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = GreenPrimary
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

@Composable
fun AppFab(
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.Add,
    contentDescription: String = "Agregar"
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = GreenPrimary,
        contentColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}

@Composable
fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        onClick = onSelect,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) GreenPrimary else GreenSecondary,
        contentColor = if (isSelected) Color.White else TextDark,
        border = if (isSelected) null else BorderStroke(1.dp, CardBorder)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun SegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(GreenSecondary)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        options.forEachIndexed { index, title ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isSelected) GreenPrimary else Color.Transparent)
                    .clickable { onOptionSelected(index) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) Color.White else TextMuted
                )
            }
        }
    }
}

@Composable
fun IconButtonWithBadge(
    icon: ImageVector = Icons.Default.Notifications,
    badgeCount: Int = 0,
    onClick: () -> Unit
) {
    Box {
        IconButton(onClick = onClick) {
            Icon(imageVector = icon, contentDescription = null, tint = TextDark)
        }
        if (badgeCount > 0) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (badgeCount > 9) "9+" else badgeCount.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppButtonsPreview() {
    PlansPPCTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SegmentedControl(
                options = listOf("Planes", "Catálogo UI", "Abonos"),
                selectedIndex = 1,
                onOptionSelected = {}
            )
            PrimaryButton(text = "Botón Primario", onClick = {})
            SecondaryButton(text = "Botón Secundario", onClick = {})
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChipItem(label = "Todos", isSelected = true, onSelect = {})
                FilterChipItem(label = "Familia", isSelected = false, onSelect = {})
                FilterChipItem(label = "Viajes", isSelected = false, onSelect = {})
            }
        }
    }
}
