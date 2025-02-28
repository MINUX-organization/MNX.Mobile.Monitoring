package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.minux.monitoring.feature.devices.impl.common.presentation.ui.DeviceTabDetail
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuSoftwareVersionsModel

@Composable
internal fun GpuSoftwareVersionsTab(
    model: GpuSoftwareVersionsModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        DeviceTabDetail(
            name = "Driver",
            value = AnnotatedString(text = model.driver ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )

        model.technologyType?.let {
            DeviceTabDetail(
                name = it,
                value = AnnotatedString(text = model.technologyVersion ?: "N/A"),
                modifier = Modifier.fillMaxWidth()
            )
        }

        DeviceTabDetail(
            name = "vBIOS",
            value = AnnotatedString(text = model.vBIOS ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )
    }
}