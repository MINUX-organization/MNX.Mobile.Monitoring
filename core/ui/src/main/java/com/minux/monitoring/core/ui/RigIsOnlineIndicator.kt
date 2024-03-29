package com.minux.monitoring.core.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RigIsOnlineIndicator(
    modifier: Modifier = Modifier,
    color: Color
) {
    Canvas(modifier = modifier) {
        drawCircle(color = color)
    }
}