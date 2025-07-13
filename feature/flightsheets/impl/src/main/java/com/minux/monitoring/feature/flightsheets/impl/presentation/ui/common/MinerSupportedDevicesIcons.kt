package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceTypeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceVendorModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.SupportedDeviceModel

@Composable
internal fun MinerSupportedDevicesIcons(
    devices: List<SupportedDeviceModel?>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        devices.forEach { device ->
            if (device == null) return@forEach

            val (deviceTypeIconId, deviceTypeDescription) = when (device.type) {
                DeviceTypeModel.Cpu -> Pair(MNXIcons.Cpu, "CPU")
                DeviceTypeModel.Gpu -> Pair(MNXIcons.Gpu, "GPU")
            }

            val (deviceVendorColor, deviceVendorDescription) = when (device.vendor) {
                DeviceVendorModel.Intel -> Pair(MaterialTheme.colorScheme.primary, "Intel")
                DeviceVendorModel.Amd -> Pair(MaterialTheme.colorScheme.secondary, "AMD")
                DeviceVendorModel.Nvidia -> Pair(MaterialTheme.colorScheme.tertiary, "Nvidia")
            }

            Icon(
                painter = painterResource(id = deviceTypeIconId),
                contentDescription = "$deviceVendorDescription $deviceTypeDescription",
                modifier = Modifier.size(20.dp),
                tint = deviceVendorColor
            )
        }
    }
}