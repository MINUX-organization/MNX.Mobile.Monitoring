package com.minux.monitoring.core.designsystem.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val TurquoiseRadialGradient = Brush.radialGradient(
    colorStops = arrayOf(
        0.1f to Color(0x503C9EA5),
        0.7f to Color(0x103C9EA5),
        1f to Color.Transparent
    ),
    radius = 450f,
    center = Offset(Float.POSITIVE_INFINITY, 50f)
)

val TurquoiseHorizontalGradient = Brush.horizontalGradient(
    colorStops = arrayOf(
        0.1f to Color(0xCD3C9EA5),
        0.4f to Color(0x803C9EA5),
        0.7f to Color(0x323C9EA5),
        1f to Color(0x193C9EA5)
    )
)

val OrangeVerticalGradient = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to Color.Transparent,
        0.5f to Color(0x19EC790F),
        1f to Color(0x50EC790F)
    )
)