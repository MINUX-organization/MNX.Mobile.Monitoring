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

fun Modifier.selectiveBorder(
    width: Dp? = null,
    color: Color? = null,
    sides: BorderSides = BorderSides(),
    shape: Shape = RectangleShape
) = this
    .clip(shape)
    .drawWithContent {
        drawContent()

        val borderSides = listOf(sides.start, sides.top, sides.end, sides.bottom)

        /**
         * Добавить поддержку RoundedCornerShape, путем дорисовывания закруклений с помощью drawArc
         * Например, если включены Bottom и Start стороны то дорисовать соотвественно в нижнем левом углу
         * Если включена одна любая сторона, то ничего не дорисовывать
         */

        borderSides.forEach { side ->
            if (side == null) return@forEach

            val sideWidth = (width ?: side.sideWidth).toPx()
            val sideColor = color ?: side.sideColor

            when (side) {
                is BorderSide.Start -> {
                    drawLine(
                        color = sideColor,
                        start = Offset(sideWidth / 2, 0f),
                        end = Offset(sideWidth / 2, size.height),
                        strokeWidth = sideWidth
                    )
                }
                is BorderSide.Top -> {
                    drawLine(
                        color = sideColor,
                        start = Offset(0f, sideWidth / 2),
                        end = Offset(size.width, sideWidth / 2),
                        strokeWidth = sideWidth
                    )
                }
                is BorderSide.End -> {
                    drawLine(
                        color = sideColor,
                        start = Offset(size.width - (sideWidth / 2), 0f),
                        end = Offset(size.width - (sideWidth / 2), size.height),
                        strokeWidth = sideWidth
                    )
                }
                is BorderSide.Bottom -> {
                    drawLine(
                        color = sideColor,
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

sealed class BorderSide(val sideWidth: Dp, val sideColor: Color) {
    data class Start(
        val width: Dp = 0.dp,
        val color: Color = Color.Transparent
    ) : BorderSide(sideWidth = width, sideColor = color)

    data class Top(
        val width: Dp = 0.dp,
        val color: Color = Color.Transparent
    ) : BorderSide(sideWidth = width, sideColor = color)

    data class End(
        val width: Dp = 0.dp,
        val color: Color = Color.Transparent
    ) : BorderSide(sideWidth = width, sideColor = color)

    data class Bottom(
        val width: Dp = 0.dp,
        val color: Color = Color.Transparent
    ) : BorderSide(sideWidth = width, sideColor = color)
}