package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.minux.monitoring.feature.devices.impl.common.presentation.ui.DeviceTabDetail
import com.minux.monitoring.feature.devices.impl.gpu.presentation.model.GpuSpecificationsModel

@Composable
internal fun GpuSpecificationsTab(
    model: GpuSpecificationsModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        DeviceTabDetail(
            name = "Manufacturer",
            value = AnnotatedString(text = model.manufacturer ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )
        
        DeviceTabDetail(
            name = "Vendor",
            value = AnnotatedString(text = model.vendor ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )
        
        DeviceTabDetail(
            name = "Memory Size",
            value = AnnotatedString(text = "${model.memorySize ?: "N/A"} Mb"),
            modifier = Modifier.fillMaxWidth()
        )
        
        DeviceTabDetail(
            name = "Memory Vendor",
            value = AnnotatedString(text = model.memoryVendor ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "Memory Type",
            value = AnnotatedString(text = model.memoryType ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )
    }
}