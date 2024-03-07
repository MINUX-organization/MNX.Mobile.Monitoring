package com.minux.monitoring.core.designsystem.theme

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.optionalBorder(
    width: Dp? = null,
    color: Color? = null,
    sides: List<BorderSide> = listOf(
        BorderSide.Start(),
        BorderSide.Top(),
        BorderSide.End(),
        BorderSide.Bottom()
    ),
    shape: Shape = RectangleShape
) = this
    .clip(shape)
    .drawWithContent {
        drawContent()

        sides.forEach { side ->
            val widthPx = (width ?: side.sideWidth).toPx()
            val sideColor = color ?: side.sideColor

            when (side) {
                is BorderSide.Start -> {
                    drawLine(
                        color = sideColor,
                        start = Offset(widthPx / 2, 0f),
                        end = Offset(widthPx / 2, size.height),
                        strokeWidth = widthPx
                    )
                }
                is BorderSide.Top -> {
                    drawLine(
                        color = sideColor,
                        start = Offset(0f, widthPx / 2),
                        end = Offset(size.width, widthPx / 2),
                        strokeWidth = widthPx
                    )
                }
                is BorderSide.End -> {
                    drawLine(
                        color = sideColor,
                        start = Offset(size.width - (widthPx / 2), 0f),
                        end = Offset(size.width - (widthPx / 2), size.height),
                        strokeWidth = widthPx
                    )
                }
                is BorderSide.Bottom -> {
                    drawLine(
                        color = sideColor,
                        start = Offset(0f, size.height - (widthPx / 2)),
                        end = Offset(size.width, size.height - (widthPx / 2)),
                        strokeWidth = widthPx
                    )
                }
            }
        }
    }

sealed class BorderSide(val sideWidth: Dp, val sideColor: Color) {
    data class Start(
        val width: Dp = (-1).dp,
        val color: Color = Color.Transparent
    ) : BorderSide(sideWidth = width, sideColor = color)

    data class Top(
        val width: Dp = (-1).dp,
        val color: Color = Color.Transparent
    ) : BorderSide(sideWidth = width, sideColor = color)

    data class End(
        val width: Dp = (-1).dp,
        val color: Color = Color.Transparent
    ) : BorderSide(sideWidth = width, sideColor = color)

    data class Bottom(
        val width: Dp = (-1).dp,
        val color: Color = Color.Transparent
    ) : BorderSide(sideWidth = width, sideColor = color)
}