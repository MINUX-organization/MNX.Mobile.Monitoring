package com.minux.monitoring.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Turquoise,
    onPrimary = Color.White,
    primaryContainer = DarkBlue30,
    onPrimaryContainer = HalfTransparentWhite,
    secondary = Red50,
    onSecondary = Color.White,
    secondaryContainer = Orange30,
    onSecondaryContainer = Orange50,
    tertiary = Green50,
    onTertiary = Color.White,
    background = DarkBlue10,
    onBackground = Color.White
)

@Composable
fun MNXTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = MNXTypography,
        content = content
    )
}