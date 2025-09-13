package com.minux.monitoring.feature.rigs.impl.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.rigs.impl.presentation.model.RigLifecycleStatusModel

@Composable
internal fun RigLifecycleButtons(
    miningStatusModel: RigLifecycleStatusModel,
    powerStatusModel: RigLifecycleStatusModel,
    onStartMiningClick: () -> Unit,
    onStopMiningClick: () -> Unit,
    onRebootClick: () -> Unit,
    onPowerOffClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        maxItemsInEachRow = 2,
        maxLines = 2
    ) {
        val isMiningExecution = miningStatusModel.run {
            this == RigLifecycleStatusModel.Enabling || this == RigLifecycleStatusModel.Disabling
        }

        MNXBorderedButton(
            onClick = onStartMiningClick,
            modifier = Modifier.weight(1f),
            enabled = !isMiningExecution
        ) {
            Text(
                text = "Start Mining",
                style = MNXTypography.bodyLarge
            )
        }

        MNXBorderedButton(
            onClick = onStopMiningClick,
            modifier = Modifier.weight(1f),
            enabled = !isMiningExecution,
            color = MaterialTheme.colorScheme.secondary
        ) {
            Text(
                text = "Stop Mining",
                style = MNXTypography.bodyLarge
            )
        }

        val isPowerExecution = powerStatusModel.run {
            this == RigLifecycleStatusModel.Enabling || this == RigLifecycleStatusModel.Disabling
        }

        MNXBorderedButton(
            onClick = onRebootClick,
            modifier = Modifier.weight(1f),
            enabled = !isPowerExecution,
            color = MaterialTheme.colorScheme.secondary
        ) {
            Text(
                text = "Reboot",
                style = MNXTypography.bodyLarge
            )
        }

        MNXBorderedButton(
            onClick = onPowerOffClick,
            modifier = Modifier.weight(1f),
            enabled = !isPowerExecution,
            color = MaterialTheme.colorScheme.secondary
        ) {
            Text(
                text = "Power Off",
                style = MNXTypography.bodyLarge
            )
        }
    }
}