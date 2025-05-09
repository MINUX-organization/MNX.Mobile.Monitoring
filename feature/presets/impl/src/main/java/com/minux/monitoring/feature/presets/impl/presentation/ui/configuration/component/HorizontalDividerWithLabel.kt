package com.minux.monitoring.feature.presets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.modifier.shimmerEffect
import com.minux.monitoring.core.designsystem.theme.MNXTheme

@Composable
internal fun HorizontalDividerWithLabel(
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.primary
        )

        ProvideTextStyle(value = TextStyle(color = MaterialTheme.colorScheme.onPrimary)) {
            label()
        }

        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun HorizontalDividerWithLabelPreview() {
    MNXTheme {
        HorizontalDividerWithLabel(
            label = { Text(text = "Sample text") }
        )
    }
}