package com.example.plansppc.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plansppc.presentation.theme.GreenPrimary
import com.example.plansppc.presentation.theme.GreenSecondary
import com.example.plansppc.presentation.theme.PlansPPCTheme
import com.example.plansppc.presentation.theme.TextDark

@Composable
fun AppAlertDialog(
    title: String,
    message: String,
    confirmText: String = "Aceptar",
    dismissText: String? = "Cancelar",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
        },
        text = {
            Text(text = message, fontSize = 14.sp, color = TextDark)
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmText, fontWeight = FontWeight.Bold, color = GreenPrimary)
            }
        },
        dismissButton = {
            if (dismissText != null) {
                TextButton(onClick = onDismiss) {
                    Text(text = dismissText, color = Color.Gray)
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White
    )
}

@Composable
fun MessageBanner(
    message: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    val bgColor = if (isError) Color(0xFFFEE2E2) else GreenSecondary
    val contentColor = if (isError) Color(0xFFDC2626) else GreenPrimary
    val icon = if (isError) Icons.Default.Error else Icons.Default.CheckCircle

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(bgColor, RoundedCornerShape(12.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = message,
            color = contentColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppDialogsPreview() {
    PlansPPCTheme {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
        ) {
            MessageBanner(message = "¡Pago registrado exitosamente!")
            MessageBanner(message = "Por favor ingrese un monto válido", isError = true)
        }
    }
}
