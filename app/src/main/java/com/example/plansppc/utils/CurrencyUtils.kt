package com.example.plansppc.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object CurrencyUtils {
    fun formatAmount(amount: Double): String {
        val symbols = DecimalFormatSymbols(Locale.GERMAN) // Uses dot for thousands separator
        val formatter = DecimalFormat("#,##0", symbols)
        return formatter.format(amount)
    }

    fun parseAmount(text: String): Double? {
        val cleaned = text.replace(".", "").replace(",", "").trim()
        return cleaned.toDoubleOrNull()
    }
}
