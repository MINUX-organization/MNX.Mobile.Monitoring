package com.minux.monitoring.feature.devices.ui.gpu.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.minux.monitoring.feature.devices.model.gpu.GPUSoftwareVersionsModel
import com.minux.monitoring.feature.devices.ui.DeviceTabDetail

@Composable
internal fun GPUSoftwareVersionsTab(
    model: GPUSoftwareVersionsModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        DeviceTabDetail(
            name = "Driver",
            value = buildAnnotatedString { append(text = model.driver) },
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "CUDA",
            value = buildAnnotatedString { append(text = model.cuda) },
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "vBIOS",
            value = buildAnnotatedString { append(text = model.vBIOS) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}