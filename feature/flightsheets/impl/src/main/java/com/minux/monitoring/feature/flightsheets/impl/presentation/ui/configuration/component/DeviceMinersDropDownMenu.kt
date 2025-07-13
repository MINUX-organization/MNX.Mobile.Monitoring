package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.modifier.shimmerEffect
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.common.MinerSupportedDevicesIcons

@Composable
internal fun DeviceMinersDropDownMenu(
    minersIsLoading: Boolean,
    miners: List<DeviceMinerModel>?,
    selectedMiner: DeviceMinerModel?,
    onSelectedMinerChange: (DeviceMinerModel?) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        label()

        when {
            minersIsLoading -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )

            miners.isNullOrEmpty() -> MNXTextField(
                value = if (miners == null) "N/A" else "No miners found",
                onValueChange = {},
                modifier = modifier,
                readOnly = true,
                supportingText = {
                    Text(
                        text = "Error to load miners",
                        modifier = Modifier.padding(start = 2.dp)
                    )
                },
                isError = miners == null
            )

            else -> MNXDropDownMenu(
                menuItems = miners,
                selectedMenuItem = selectedMiner,
                onSelectedMenuItemChange = onSelectedMinerChange,
                suffix = { miner ->
                    miner?.supportedDevices?.let {
                        MinerSupportedDevicesIcons(devices = it)
                    }
                }
            )
        }
    }
}