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
        val density = LocalDensity.current
        val drawPadding = with(density) { MNXSliderTokens.ContentPadding.toPx() }
        val labelPadding = with(density) { MNXSliderTokens.LabelsVerticalPadding.toPx() }

        val textMeasurer = rememberTextMeasurer()

        val lineSize = with(density) { MNXSliderTokens.CurrentValueIndicatorSize.toSize() }
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
                        valueRange = state.valueRange,
                        contentWidth = size.width,
                        contentPadding = drawPadding,
                        labelPadding = labelPadding
                    )

                    drawSliderLabels(
                        sliderState = state,
                        sliderOffset = sliderOffset,
                        measurer = textMeasurer
                    )

                    drawSliderCurrentValueIndicator(
                        sliderState = state,
                        sliderOffset = sliderOffset,
                        indicatorSize = lineSize,
                        indicatorColor = lineColor
                    )
                },
            valueRange = state.valueRange,
            track = {
                MNXSliderDefaults.Track(sliderState = state)
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
        sliderState: MNXSliderState,
        modifier: Modifier = Modifier,
        shape: Shape = RectangleShape
    ) {
        val values = with(sliderState.valueRange) {
            (start.toInt()..endInclusive.toInt()).toList()
        }

        val valueIndex = values.indexOf(sliderState.currentValue.toInt())

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(MNXSliderTokens.TrackHeight)
                .background(
                    color = MNXSliderTokens.InactiveTrackColor,
                    shape = shape
                )
        )

        Box(
            modifier = modifier
                .fillMaxWidth(valueIndex.toFloat() / values.size)
                .height(MNXSliderTokens.TrackHeight)
                .background(
                    color = MNXSliderTokens.ActiveTrackColor,
                    shape = shape
                )
                .border(
                    width = MNXSliderTokens.TrackBorderWidth,
                    brush = TurquoiseHorizontalGradient,
                    shape = shape
                )
        )
    }
}

private object MNXSliderTokens {
    val ActiveTrackColor = Color(0xFF111111)
    val ContentPadding = 10.dp
    val CurrentValueIndicatorSize = DpSize(1.dp, 18.dp)
    val InactiveTrackColor = Color.Black
    val LabelsVerticalPadding = 20.dp
    val TrackHeight = 6.dp
    val TrackBorderWidth = 0.5.dp
}

private class SliderOffset(
    valueRange: ClosedFloatingPointRange<Float>,
    contentWidth: Float,
    private val contentPadding: Float,
    private val labelPadding: Float,
) {
    private val values = with(valueRange) { start.toInt()..endInclusive.toInt() }.toList()
    private val stepSize = contentWidth.minus(2 * contentPadding).div(values.size - 1)

    fun getIndicatorOffset(value: Float): Float {
        val index = values.indexOf(value.toInt())

        return if (index != -1)
            contentPadding + index.times(stepSize)
        else
            contentPadding
    }

    fun getLabelOffset(value: Float, labelType: LabelType): Offset {
        val labelOffset = when (labelType) {
            is LabelType.Indicator -> labelType.measuredText.size.width / 2
            LabelType.Start -> 0
            is LabelType.End -> labelType.measuredText.size.width
        }

        return Offset(
            x = getIndicatorOffset(value) - labelOffset,
            y = -labelPadding
        )
    }
}

private sealed interface LabelType {
    data class Indicator(val measuredText: TextLayoutResult) : LabelType

    data object Start : LabelType

    data class End(val measuredText: TextLayoutResult) : LabelType
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
    measurer: TextMeasurer
) {
    val startLabelPosition = drawStartSliderLabel(
        value = sliderState.valueRange.start,
        textMeasurer = measurer,
        offset = sliderOffset
    )

    val endLabelPosition = drawEndSliderLabel(
        value = sliderState.valueRange.endInclusive,
        textMeasurer = measurer,
        offset = sliderOffset
    )

    drawSliderIndicatorLabels(
        value = sliderState.value,
        currentValue = sliderState.currentValue,
        offset = sliderOffset,
        textMeasurer = measurer,
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
        labelType = LabelType.Indicator(measuredText = thumbLabelMeasuredText)
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
        labelType = LabelType.Indicator(measuredText = currentValueLabelMeasuredText)
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

    val labelOffset = offset.getLabelOffset(value = value, labelType = LabelType.Start)

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

    val labelOffset = offset.getLabelOffset(
        value = value,
        labelType = LabelType.End(measuredText = labelMeasuredText)
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
            currentValue = 1700f,
            valueRange = 800f..3000f
        )
    }
}