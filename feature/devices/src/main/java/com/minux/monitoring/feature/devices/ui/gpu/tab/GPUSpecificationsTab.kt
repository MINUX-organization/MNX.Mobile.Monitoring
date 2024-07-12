package com.minux.monitoring.feature.devices.ui.gpu.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.minux.monitoring.feature.devices.model.gpu.GPUSpecificationsModel
import com.minux.monitoring.feature.devices.ui.DeviceTabDetail

@Composable
internal fun GPUSpecificationsTab(
    model: GPUSpecificationsModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        DeviceTabDetail(
            name = "Manufacturer",
            value = buildAnnotatedString { append(text = model.manufacturer) },
            modifier = Modifier.fillMaxWidth()
        )
        
        DeviceTabDetail(
            name = "Vendor",
            value = buildAnnotatedString { append(text = model.vendor) },
            modifier = Modifier.fillMaxWidth()
        )
        
        DeviceTabDetail(
            name = "Memory Size",
            value = buildAnnotatedString {
                append(text = "${model.memorySize} ${model.memorySizeUnit}")
            },
            modifier = Modifier.fillMaxWidth()
        )
        
        DeviceTabDetail(
            name = "Memory Vendor",
            value = buildAnnotatedString { append(text = model.memoryVendor) },
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "Memory Type",
            value = buildAnnotatedString { append(text = model.memoryType) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}