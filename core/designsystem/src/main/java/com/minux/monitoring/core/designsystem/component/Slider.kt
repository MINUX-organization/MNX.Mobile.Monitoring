package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.TurquoiseHorizontalGradient
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MNXSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    currentValue: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        val textMeasurer = rememberTextMeasurer()
        val density = LocalDensity.current
        val drawPadding = with(LocalDensity.current) { 10.dp.toPx() }
        val lineSize = with(LocalDensity.current) {
            DpSize(
                width = MNXSliderTokens.CurrentValueIndicatorWidth,
                height = 18.dp
            ).toSize()
        }
        val lineColor = MaterialTheme.colorScheme.primary

        val state = remember(value) {
            MNXSliderState(
                value = value,
                currentValue = currentValue,
                valueRange = valueRange
            )
        }

        Slider(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .padding(vertical = 20.dp)
                .drawWithContent {
                    drawContent()

                    val sliderOffset = SliderOffset(
                        valueRange = valueRange,
                        contentWidth = size.width,
                        contentPadding = drawPadding,
                        density = density,
                        labelPadding = MNXSliderTokens.LabelsVerticalPadding
                    )

                    drawSliderLabels(
                        sliderState = state,
                        sliderOffset = sliderOffset,
                        textMeasurer = textMeasurer
                    )

                    drawSliderCurrentValueIndicator(
                        sliderState = state,
                        sliderOffset = sliderOffset,
                        indicatorSize = lineSize,
                        indicatorColor = lineColor
                    )
                },
            valueRange = valueRange,
            track = {
                MNXSliderDefaults.Track()
            }
        )
    }
}

private class MNXSliderState(
    val value: Float,
    val currentValue: Float,
    val valueRange: ClosedFloatingPointRange<Float>
)

private object MNXSliderDefaults {
    @Composable
    fun Track(
        modifier: Modifier = Modifier,
        shape: Shape = RectangleShape
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(
                    color = Color(0xFF111111),
                    shape = shape
                )
                .border(
                    width = 0.5.dp,
                    brush = TurquoiseHorizontalGradient,
                    shape = shape
                )
        )
    }
}

private object MNXSliderTokens {
    val CurrentValueIndicatorWidth = 1.dp
    val LabelsVerticalPadding = 20.dp
}

private class SliderOffset(
    private val valueRange: ClosedFloatingPointRange<Float>,
    private val contentWidth: Float,
    private val contentPadding: Float,
    private val density: Density,
    private val labelPadding: Dp,
) {
    fun getIndicatorOffset(value: Float): Float {
        val values = (valueRange.start.toInt()..valueRange.endInclusive.toInt()).toList()

        val distance = (contentWidth.minus(2 * contentPadding))
            .div(values.size.minus(1))

        val index = values.indexOf(value.toInt())

        return contentPadding + index.times(distance)
    }

    fun getLabelOffset(value: Float, measuredText: TextLayoutResult): Offset {
        return Offset(
            x = getIndicatorOffset(value) - (measuredText.size.width / 2),
            y = with(density) { -labelPadding.toPx() }
        )
    }

    fun getStartLabelOffset(value: Float): Offset {
        return Offset(
            x = getIndicatorOffset(value),
            y = with(density) { -labelPadding.toPx() }
        )
    }

    fun getEndLabelOffset(value: Float, measuredText: TextLayoutResult): Offset {
        return Offset(
            x = getIndicatorOffset(value) - measuredText.size.width,
            y = with(density) { -labelPadding.toPx() }
        )
    }
}

private fun DrawScope.drawSliderCurrentValueIndicator(
    sliderState: MNXSliderState,
    sliderOffset: SliderOffset,
    indicatorSize: Size,
    indicatorColor: Color
) {
    val indicatorOffset = sliderOffset.getIndicatorOffset(value = sliderState.currentValue)

    drawLine(
        color = indicatorColor,
        start = Offset(x = indicatorOffset, y = 4f),
        end = Offset(x = indicatorOffset, y = indicatorSize.height),
        strokeWidth = indicatorSize.width
    )
}

private fun DrawScope.drawSliderLabels(
    sliderState: MNXSliderState,
    sliderOffset: SliderOffset,
    textMeasurer: TextMeasurer
) {
    val startLabelPosition = drawStartSliderLabel(
        value = sliderState.valueRange.start,
        textMeasurer = textMeasurer,
        offset = sliderOffset
    )

    val endLabelPosition = drawEndSliderLabel(
        value = sliderState.valueRange.endInclusive,
        textMeasurer = textMeasurer,
        offset = sliderOffset
    )

    drawSliderIndicatorLabels(
        value = sliderState.value,
        currentValue = sliderState.currentValue,
        offset = sliderOffset,
        textMeasurer = textMeasurer,
        startLabelPosition = startLabelPosition,
        endLabelPosition = endLabelPosition
    )
}

private val labelStyle = TextStyle(
    color = Color.White,
    fontSize = 16.sp,
    fontFamily = grillSansMtFamily,
    textAlign = TextAlign.Center
)

private fun DrawScope.drawSliderIndicatorLabels(
    value: Float,
    currentValue: Float,
    offset: SliderOffset,
    textMeasurer: TextMeasurer,
    startLabelPosition: Float,
    endLabelPosition: Float
) {
    val thumbLabelMeasuredText = textMeasurer.measure(
        text = value.toInt().toString(),
        style = labelStyle
    )

    val thumbLabelOffset = offset.getLabelOffset(
        value = value,
        measuredText = thumbLabelMeasuredText
    )

    val thumbLabelPositions = Pair(
        first = thumbLabelOffset.x,
        second = thumbLabelOffset.x + thumbLabelMeasuredText.size.width
    )

    val currentValueLabelMeasuredText = textMeasurer.measure(
        text = currentValue.toInt().toString(),
        style = labelStyle
    )

    val currentValueLabelOffset = offset.getLabelOffset(
        value = currentValue,
        measuredText = currentValueLabelMeasuredText
    )

    val currentValueLabelPositions = Pair(
        first = currentValueLabelOffset.x,
        second = currentValueLabelOffset.x + currentValueLabelMeasuredText.size.width
    )

    if (thumbLabelPositions.first >= startLabelPosition &&
        thumbLabelPositions.second <= currentValueLabelPositions.first ||
        thumbLabelPositions.first >= currentValueLabelPositions.second &&
        thumbLabelPositions.second <= endLabelPosition
    ) {
        drawText(
            textLayoutResult = thumbLabelMeasuredText,
            topLeft = Offset(x = thumbLabelOffset.x, y = thumbLabelOffset.y)
        )
    }

    if (currentValueLabelPositions.first >= startLabelPosition &&
        currentValueLabelPositions.second <= endLabelPosition
    ) {
        drawText(
            textLayoutResult = currentValueLabelMeasuredText,
            topLeft = Offset(x = currentValueLabelOffset.x, y = thumbLabelOffset.y)
        )
    }
}

private fun DrawScope.drawStartSliderLabel(
    value: Float,
    textMeasurer: TextMeasurer,
    offset: SliderOffset
): Float {
    val labelMeasuredText = textMeasurer.measure(
        text = value.toInt().toString(),
        style = labelStyle
    )

    val labelOffset = offset.getStartLabelOffset(value = value)

    drawText(
        textLayoutResult = labelMeasuredText,
        topLeft = Offset(x = labelOffset.x, y = labelOffset.y)
    )

    return labelOffset.x + labelMeasuredText.size.width
}

private fun DrawScope.drawEndSliderLabel(
    value: Float,
    textMeasurer: TextMeasurer,
    offset: SliderOffset
): Float {
    val labelMeasuredText = textMeasurer.measure(
        text = value.toInt().toString(),
        style = labelStyle
    )

    val labelOffset = offset.getEndLabelOffset(
        value = value,
        measuredText = labelMeasuredText
    )

    drawText(
        textLayoutResult = labelMeasuredText,
        topLeft = Offset(x = labelOffset.x, y = labelOffset.y)
    )

    return labelOffset.x
}

@Preview
@Composable
private fun MNXSliderPreview() {
    MNXTheme {
        val number = remember {
            mutableFloatStateOf(2200f)
        }

        MNXSlider(
            value = number.floatValue,
            onValueChange = { number.floatValue = it },
            currentValue = 1200f,
            valueRange = 800f..3000f
        )
    }
}