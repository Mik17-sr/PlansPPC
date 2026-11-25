package com.example.plansppc

import com.example.plansppc.utils.CurrencyUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyUtilsTest {

    @Test
    fun testFormatAmount() {
        val formatted = CurrencyUtils.formatAmount(5000000.0)
        assertEquals("5.000.000", formatted)
    }

    @Test
    fun testParseAmount() {
        val parsed = CurrencyUtils.parseAmount("5.000.000")
        assertEquals(5000000.0, parsed!!, 0.001)
    }
}
