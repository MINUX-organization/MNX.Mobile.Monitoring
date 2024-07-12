package com.minux.monitoring.feature.devices.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.devices.deviceTextStyle

@Composable
internal fun DeviceTabDetail(
    name: String,
    value: AnnotatedString,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = deviceTextStyle
        )

        Text(
            text = value,
            color = MaterialTheme.colorScheme.onBackground,
            style = deviceTextStyle
        )
    }
}

@Preview
@Composable
internal fun DeviceTabDetailPreview() {
    MNXTheme {
        DeviceTabDetail(
            name = "Detail name",
            value = buildAnnotatedString { append("Detail value") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}