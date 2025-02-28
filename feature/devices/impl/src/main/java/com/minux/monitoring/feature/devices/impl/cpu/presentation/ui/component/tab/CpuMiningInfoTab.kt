package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.devices.impl.common.presentation.ui.DeviceTabDetail
import com.minux.monitoring.feature.devices.impl.cpu.presentation.model.CpuMiningInfoModel

@Composable
internal fun CpuMiningInfoTab(
    model: CpuMiningInfoModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        DeviceTabDetail(
            name = "Flight sheet",
            value = AnnotatedString(text = model.flightSheetName ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )

        DeviceTabDetail(
            name = "Miner",
            value = AnnotatedString(text = model.minerName ?: "N/A"),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun GpuMiningInfoTabPreview() {
    MNXTheme {
        CpuMiningInfoTab(
            model = CpuMiningInfoModel(
                flightSheetName = "",
                minerName = null
            )
        )
    }
}