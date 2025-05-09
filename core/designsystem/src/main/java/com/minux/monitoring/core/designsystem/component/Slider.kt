package com.minux.monitoring.core.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
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
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.theme.DarkBlue10
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.designsystem.theme.Turquoise

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MNXSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
        val density = LocalDensity.current
        val drawPadding = with(density) { MNXSliderTokens.ContentPadding.toPx() }

        val labelPadding = with(density) { MNXSliderTokens.LabelsVerticalPadding.toPx() }
        val textMeasurer = rememberTextMeasurer()

        val state = remember(value) {
            MNXSliderState(
                value = value,
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
                        contentSize = size,
                        contentPadding = drawPadding,
                        labelPadding = labelPadding
                    )

                    drawSliderLabels(
                        sliderState = state,
                        sliderOffset = sliderOffset,
                        measurer = textMeasurer
                    )
                },
            valueRange = state.valueRange,
            thumb = {
                MNXSliderDefaults.Thumb()
            },
            track = {
                MNXSliderDefaults.Track(sliderState = state)
            }
        )
    }
}

private object MNXSliderTokens {
    val ActiveTrackColor = Turquoise.copy(alpha = 0.5f)
    val ContentPadding = 10.dp
    val InactiveTrackColor = DarkBlue10
    val LabelsVerticalPadding = 20.dp
    val TrackHeight = 6.dp
    val TrackBorderWidth = 0.5.dp
}

private object MNXSliderDefaults {

    @Composable
    fun Thumb(modifier: Modifier = Modifier) {
        val thumbColor = MaterialTheme.colorScheme.primary

        Canvas(modifier = modifier.size(20.dp)) {
            drawCircle(color = thumbColor)
        }
    }

    @Composable
    fun Track(
        sliderState: MNXSliderState,
        modifier: Modifier = Modifier,
        shape: Shape = RectangleShape
    ) {
        val values = with(sliderState.valueRange) {
            (start.toInt()..endInclusive.toInt()).toList()
        }

        val valueIndex = values.indexOf(sliderState.value.toInt())

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(MNXSliderTokens.TrackHeight)
                .background(
                    color = MNXSliderTokens.InactiveTrackColor,
                    shape = shape
                )
                .border(
                    width = MNXSliderTokens.TrackBorderWidth,
                    color = MaterialTheme.colorScheme.primary,
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
        )
    }
}

private class MNXSliderState(
    val value: Float,
    val valueRange: ClosedFloatingPointRange<Float>
)

private class SliderOffset(
    valueRange: ClosedFloatingPointRange<Float>,
    private val contentSize: Size,
    private val contentPadding: Float,
    private val labelPadding: Float,
) {
    private val values = with(valueRange) { start.toInt()..endInclusive.toInt() }.toList()
    private val stepSize = contentSize.width
        .minus(2 * contentPadding)
        .div(values.size - 1)

    fun getIndicatorOffset(value: Float): Offset {
        val index = values.indexOf(value.toInt())

        val indicatorOffset = if (index != -1)
            contentPadding + index.times(stepSize)
        else
            contentPadding

        return Offset(
            x = indicatorOffset,
            y = contentSize.height / 2
        )
    }

    fun getLabelOffset(value: Float, labelType: LabelType): Offset {
        val labelOffset = when (labelType) {
            is LabelType.Indicator -> labelType.measuredText.size.width / 2
            LabelType.Start -> 0
            is LabelType.End -> labelType.measuredText.size.width
        }

        return Offset(
            x = getIndicatorOffset(value).x - labelOffset,
            y = -labelPadding
        )
    }
}

private sealed interface LabelType {
    class Indicator(val measuredText: TextLayoutResult) : LabelType

    data object Start : LabelType

    class End(val measuredText: TextLayoutResult) : LabelType
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
        offset = sliderOffset,
        textMeasurer = measurer,
        startLabelPosition = startLabelPosition,
        endLabelPosition = endLabelPosition
    )
}

private val labelStyle = MNXTypography.bodyLarge.copy(
    color = Color.White,
    textAlign = TextAlign.Center
)

private fun DrawScope.drawSliderIndicatorLabels(
    value: Float,
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

    if (thumbLabelPositions.first >= startLabelPosition && thumbLabelPositions.second <= endLabelPosition) {
        drawText(
            textLayoutResult = thumbLabelMeasuredText,
            topLeft = Offset(x = thumbLabelOffset.x, y = thumbLabelOffset.y)
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
            mutableFloatStateOf(2500f)
        }

        MNXSlider(
            value = number.floatValue,
            onValueChange = { number.floatValue = it },
            valueRange = 800f..3000f
        )
    }
}