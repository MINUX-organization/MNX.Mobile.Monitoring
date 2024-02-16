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
    secondary = Red,
    onSecondary = Color.White,
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