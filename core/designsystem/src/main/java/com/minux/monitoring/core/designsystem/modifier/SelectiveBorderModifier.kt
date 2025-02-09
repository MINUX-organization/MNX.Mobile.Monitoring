package com.minux.monitoring.core.designsystem.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.selectiveBorder(
    sides: BorderSides = BorderSides(
        start = BorderSide.Start(width = 1.dp),
        top = BorderSide.Top(width = 1.dp),
        end = BorderSide.End(width = 1.dp),
        bottom = BorderSide.Bottom(width = 1.dp)
    ),
    color: Color
) = then(
    clip(RectangleShape)
        .drawWithContent {
            drawContent()

            drawBorderSides(
                sides = listOf(sides.start, sides.top, sides.end, sides.bottom),
                color = color
            )
        }
)

private fun ContentDrawScope.drawBorderSides(
    sides: List<BorderSide?>,
    color: Color
) {
    sides.forEach { side ->
        if (side == null) return@forEach

        val sideWidth = side.sideWidth.toPx()

        when (side) {
            is BorderSide.Start -> {
                drawLine(
                    color = color,
                    start = Offset(sideWidth / 2, 0f),
                    end = Offset(sideWidth / 2, size.height),
                    strokeWidth = sideWidth
                )
            }

            is BorderSide.Top -> {
                drawLine(
                    color = color,
                    start = Offset(0f, sideWidth / 2),
                    end = Offset(size.width, sideWidth / 2),
                    strokeWidth = sideWidth
                )
            }

            is BorderSide.End -> {
                drawLine(
                    color = color,
                    start = Offset(size.width - (sideWidth / 2), 0f),
                    end = Offset(size.width - (sideWidth / 2), size.height),
                    strokeWidth = sideWidth
                )
            }

            is BorderSide.Bottom -> {
                drawLine(
                    color = color,
                    start = Offset(0f, size.height - (sideWidth / 2)),
                    end = Offset(size.width, size.height - (sideWidth / 2)),
                    strokeWidth = sideWidth
                )
            }
        }
    }
}

data class BorderSides(
    val start: BorderSide.Start? = null,
    val top: BorderSide.Top? = null,
    val end: BorderSide.End? = null,
    val bottom: BorderSide.Bottom? = null
)

sealed class BorderSide(val sideWidth: Dp) {
    class Start(val width: Dp) : BorderSide(sideWidth = width)

    class Top(val width: Dp) : BorderSide(sideWidth = width)

    class End(val width: Dp) : BorderSide(sideWidth = width)

    class Bottom(val width: Dp) : BorderSide(sideWidth = width)
}