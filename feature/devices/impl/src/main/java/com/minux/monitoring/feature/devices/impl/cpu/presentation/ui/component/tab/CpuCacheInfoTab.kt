package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.minux.monitoring.feature.devices.impl.common.presentation.ui.DeviceTabDetail
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuCacheInfoModel

@Composable
internal fun CpuCacheInfoTab(
    model: CpuCacheInfoModel?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        DeviceTabDetail(
            name = "L1 Size",
            value = AnnotatedString(text = "${model?.l1Size ?: "N/A"} KB"),
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "L2 Size",
            value = AnnotatedString(text = "${model?.l2Size ?: "N/A"} MB"),
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "L3 Size",
            value = AnnotatedString(text = "${model?.l3Size ?: "N/A"} MB"),
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "L4 Size",
            value = AnnotatedString(text = "${model?.l4Size ?: "N/A"} MB"),
            modifier = Modifier.fillMaxWidth()
        )
    }
}