package com.minux.monitoring.feature.presets.impl.presentation.ui.common

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.minux.monitoring.feature.presets.impl.presentation.model.DeviceParameterModel

@Composable
internal fun DeviceParameter(
    model: DeviceParameterModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .basicMarquee(
                iterations = Int.MAX_VALUE,
                velocity = 45.dp
            )
    ) {
        Text(
            text = model.name,
            modifier = Modifier.padding(end = 4.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = buildAnnotatedString {
                append(text = "${model.value ?: "N/A"} ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(text = model.valueUnit)
                }
            },
            modifier = Modifier.padding(start = 4.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}