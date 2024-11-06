package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXSlider
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.feature.presets.impl.presentation.model.PresetOptionModel

@Composable
internal fun PresetOption(
    optionValue: Float,
    onOptionValueChange: (Float) -> Unit,
    model: PresetOptionModel,
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
                Text(text = model.optionName)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OptionValueField(
                        value = optionValue,
                        onValueChange = onOptionValueChange,
                        valueRange = model.optionValueRange
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(text = model.optionValueUnit)
                }
            }
        }

        MNXSlider(
            value = optionValue,
            onValueChange = onOptionValueChange,
            currentValue = model.optionCurrentValue,
            valueRange = model.optionValueRange
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
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MNXTextField(
            value = value.toInt().toString(),
            onValueChange = {
                val number = it.toFloat()

                if (number in valueRange) {
                    onValueChange(number)
                }
            },
            modifier = Modifier
                .size(
                    width = 48.dp,
                    height = 28.dp
                )
                .then(modifier),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RectangleShape,
            contentPadding = PaddingValues(
                start = 2.dp,
                end = 8.dp
            )
        )
    }
}

@Preview
@Composable
private fun PresetOptionPreview() {
    MNXTheme {
        val value = remember {
            mutableFloatStateOf(1700f)
        }

        PresetOption(
            optionValue = value.floatValue,
            onOptionValueChange = { value.floatValue = it },
            model = PresetOptionModel(
                optionName = "Sample name",
                optionValueUnit = "Unit",
                optionCurrentValue = 1500f,
                optionValueRange = 0f..3000f
            )
        )
    }
}