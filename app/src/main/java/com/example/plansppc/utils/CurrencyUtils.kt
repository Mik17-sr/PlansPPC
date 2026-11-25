package com.example.plansppc.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyUtils {
    /**
     * Formats a Double amount to Spanish (Colombia) currency string without symbol (e.g. 5000000.0 -> "5.000.000")
     */
    fun formatAmount(amount: Double): String {
        val formatter = NumberFormat.getInstance(Locale("es", "CO"))
        return formatter.format(amount)
    }

    /**
     * Formats money input dynamically as the user types digits.
     * Inserts dots as thousands separators: "5000000" -> "5.000.000"
     */
    fun formatMoneyInput(rawInput: String, previousInput: String = ""): String {
        var digits = rawInput.filter { it.isDigit() }
        val prevDigits = previousInput.filter { it.isDigit() }

        // If user pressed backspace over a dot, drop one digit so backspace feels natural
        if (digits.length == prevDigits.length && rawInput.length < previousInput.length && digits.isNotEmpty()) {
            digits = digits.dropLast(1)
        }

        if (digits.isEmpty()) return ""
        val parsed = digits.toLongOrNull() ?: return ""
        val formatter = NumberFormat.getInstance(Locale("es", "CO"))
        return formatter.format(parsed)
    }

    /**
     * Cleans formatted input string (removing dots) and parses as Double.
     */
    fun cleanMoneyInput(formatted: String): Double {
        val digits = formatted.filter { it.isDigit() }
        return digits.toDoubleOrNull() ?: 0.0
    }
}
