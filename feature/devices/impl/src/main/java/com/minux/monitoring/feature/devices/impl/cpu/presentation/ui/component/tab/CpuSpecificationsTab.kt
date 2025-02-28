package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.minux.monitoring.feature.devices.impl.common.presentation.ui.DeviceTabDetail
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuSpecificationsModel

@Composable
internal fun CpuSpecificationsTab(
    model: CpuSpecificationsModel?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        DeviceTabDetail(
            name = "Manufacturer",
            value = AnnotatedString(text = model?.manufacturer ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "Cores Count",
            value = AnnotatedString(text = model?.coresCount?.toString() ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "Threads Count",
            value = AnnotatedString(text = model?.threadsCount?.toString() ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "Architecture",
            value = AnnotatedString(text = model?.architecture ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )
    }
}