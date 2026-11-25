package com.example.plansppc.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    prefix: @Composable (() -> Unit)? = null
) {
    LabeledInputField(
        label = label,
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        prefix = prefix
    )
}
