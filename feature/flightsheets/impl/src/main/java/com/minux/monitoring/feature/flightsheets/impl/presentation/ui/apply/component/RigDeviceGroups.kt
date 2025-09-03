package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.apply.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.shimmerEffect
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.DeviceGroupItemModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.DeviceItemModel

@Composable
internal fun RigDeviceGroups(
    rigDeviceGroups: List<DeviceGroupItemModel<DeviceGroupItemModel<DeviceItemModel>>>,
    onAllDevicesOnRigCheckedChange: (rigIndex: Int, checked: Boolean) -> Unit,
    onDeviceCheckedChange: (id: String, checked: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(rigDeviceGroups) { rig ->
            RigDeviceGroupItem(
                model = rig,
                onAllDevicesCheckedChange = {
                    onAllDevicesOnRigCheckedChange(rigDeviceGroups.indexOf(rig), it)
                },
                onDeviceCheckedChange = onDeviceCheckedChange
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
internal fun RigDevicesGroupsShimmer() {
    Column {
        repeat(8) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
internal fun RigDeviceGroupsError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = MNXIcons.MinuxError),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Failed to load devices",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MNXTypography.titleSmall
        )
    }
}