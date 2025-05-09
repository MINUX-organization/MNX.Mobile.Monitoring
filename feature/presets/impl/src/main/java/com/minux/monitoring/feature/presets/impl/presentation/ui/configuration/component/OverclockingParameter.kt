package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXSlider
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.modifier.shimmerEffect
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.presets.impl.presentation.model.OverclockingParameterModel

@Composable
internal fun OverclockingParameter(
    optionValue: Float,
    onOptionValueChange: (Float) -> Unit,
    model: OverclockingParameterModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProvideTextStyle(value = TextStyle(color = MaterialTheme.colorScheme.onPrimary)) {
                Text(text = model.name)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OptionValueField(
                        value = optionValue,
                        onValueChange = onOptionValueChange,
                        valueRange = model.valueRange
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(text = model.valueUnit)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))

        MNXSlider(
            value = optionValue,
            onValueChange = onOptionValueChange,
            valueRange = model.valueRange
        )
    }
}

@Composable
internal fun OverclockingParameterShimmer() {
    Column {
        Box(
            modifier = Modifier
                .size(width = 120.dp, height = 20.dp)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .shimmerEffect()
        )
    }
}

@Composable
private fun OptionValueField(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier
) {
    MNXTextField(
        value = value.toInt().toString(),
        onValueChange = {
            val number = it.toFloatOrNull()

            number?.run {
                when {
                    number in valueRange -> onValueChange(number)

                    number < valueRange.start -> onValueChange(valueRange.start)

                    number > valueRange.endInclusive -> onValueChange(valueRange.endInclusive)
                }
            }
        },
        modifier = Modifier
            .width(48.dp)
            .wrapContentWidth()
            .heightIn(min = 28.dp)
            .then(modifier),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RectangleShape,
        contentPadding = PaddingValues(horizontal = 4.dp)
    )
}

@Preview
@Composable
private fun OverclockingParameterPreview() {
    MNXTheme {
        val value = remember {
            mutableFloatStateOf(1700f)
        }

        OverclockingParameter(
            optionValue = value.floatValue,
            onOptionValueChange = { value.floatValue = it },
            model = OverclockingParameterModel(
                name = "Sample name",
                valueUnit = "Unit",
                defaultValue = 1500f,
                valueRange = 0f..3000f
            )
        )
    }
}

@Preview
@Composable
private fun OverclockingParameterShimmerPreview() {
    MNXTheme {
        Surface {
            OverclockingParameterShimmer()
        }
    }
}