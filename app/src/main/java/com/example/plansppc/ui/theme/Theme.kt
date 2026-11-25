package com.example.plansppc.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    primaryContainer = GreenSecondary,
    onPrimaryContainer = GreenPrimaryHover,
    secondary = GreenPrimaryHover,
    onSecondary = Color.White,
    background = GreenBackground,
    onBackground = TextDark,
    surface = SurfaceWhite,
    onSurface = TextDark,
    outline = CardBorder
)

@Composable
fun PlansPPCTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
