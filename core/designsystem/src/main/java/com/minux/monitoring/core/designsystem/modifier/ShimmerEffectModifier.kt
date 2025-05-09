package com.minux.monitoring.core.designsystem.modifier

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.minux.monitoring.core.designsystem.theme.Turquoise

@Composable
fun Modifier.shimmerEffect(durationMillis: Int = 2000): Modifier {
    var size by remember { mutableStateOf(IntSize.Zero) }

    val transition = rememberInfiniteTransition(label = "shimmer")

    val translateAnimation by transition.animateFloat(
        initialValue = -1.5f * size.width.toFloat(),
        targetValue = 1.5f * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            )
        ),
        label = "blinking",
    )

    return this then Modifier.drawBehind {
        val color = Turquoise

        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    color.copy(0.05f),
                    color.copy(0.1f),
                    color.copy(0.3f),
                    color.copy(0.5f),
                    color.copy(0.3f),
                    color.copy(0.1f),
                    color.copy(0.05f)
                ),
                start = Offset(x = translateAnimation, y = 0f),
                end = Offset(x = translateAnimation + size.width.toFloat(), y = size.height.toFloat()),
            )
        )
    }.onGloballyPositioned {
        size = it.size
    }
}