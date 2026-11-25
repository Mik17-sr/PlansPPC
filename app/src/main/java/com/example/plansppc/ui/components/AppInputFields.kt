package com.example.plansppc.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import com.example.plansppc.ui.theme.CardBorder
import com.example.plansppc.ui.theme.GreenPrimary
import com.example.plansppc.ui.theme.GreenSecondary
import com.example.plansppc.ui.theme.PlansPPCTheme
import com.example.plansppc.ui.theme.TextDark
import com.example.plansppc.ui.theme.TextMuted

@Composable
fun LabeledInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    supportingText: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    prefix: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = TextDark
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            placeholder = { if (placeholder.isNotEmpty()) Text(placeholder, color = TextMuted) },
            isError = isError,
            prefix = prefix,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GreenPrimary,
                unfocusedBorderColor = CardBorder,
                errorBorderColor = Color.Red
            ),
            singleLine = true
        )
        if (!errorMessage.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = errorMessage, color = Color.Red, fontSize = 12.sp)
        } else if (!supportingText.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = supportingText, color = TextMuted, fontSize = 12.sp)
        }
    }
}

@Composable
fun SearchInputField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Buscar plan o miembro..."
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        placeholder = { Text(placeholder, color = TextMuted, fontSize = 14.sp) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar", tint = TextMuted)
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GreenPrimary,
            unfocusedBorderColor = CardBorder,
            focusedContainerColor = GreenSecondary.copy(alpha = 0.5f),
            unfocusedContainerColor = GreenSecondary.copy(alpha = 0.5f)
        ),
        singleLine = true
    )
}

@Composable
fun <T> AppDropdownMenu(
    label: String,
    items: List<T>,
    selectedItem: T?,
    itemLabel: (T) -> String,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Seleccionar opción"
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = TextDark
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedItem?.let(itemLabel) ?: placeholder,
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
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = itemLabel(item), fontSize = 16.sp, color = TextDark) },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppInputFieldsPreview() {
    PlansPPCTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
        ) {
            SearchInputField(query = "", onQueryChange = {})

            LabeledInputField(
                label = "Motivo del plan",
                value = "",
                onValueChange = {},
                placeholder = "Ej: Viaje familiar"
            )

            AppDropdownMenu(
                label = "Seleccionar Miembro",
                items = listOf("Jorge", "Ana", "Luis"),
                selectedItem = "Jorge",
                itemLabel = { it },
                onItemSelected = {}
            )
        }
    }
}
