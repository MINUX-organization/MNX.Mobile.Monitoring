package com.minux.monitoring.feature.monitoring.impl.presentation.ui.component.rig

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigControlModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigItemModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigMiningState
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.RigPowerState
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.model.MonitoringEvent

@Composable
internal fun RigControlButtons(
    rigItem: RigItemModel,
    onRigControlEvent: (MonitoringEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            val controlMiningEvent = MonitoringEvent.ControlMiningOnRig(
                rigIdentificationModel = rigItem.identification
            )

            RigMiningControlButton(
                onClick = { onRigControlEvent(controlMiningEvent) },
                model = rigItem.control,
                modifier = Modifier.weight(1f)
            )

            val powerOffRigEvent = MonitoringEvent.PowerOffRig(
                rigIdentificationModel = rigItem.identification
            )

            RigPowerControlButton(
                onClick = { onRigControlEvent(powerOffRigEvent) },
                model = rigItem.control,
                modifier = Modifier.weight(1f)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            val rebootRigEvent = MonitoringEvent.RebootRig(
                rigIdentificationModel = rigItem.identification
            )

            RigRebootControlButton(
                onClick = { onRigControlEvent(rebootRigEvent) },
                model = rigItem.control,
                modifier = Modifier.weight(1f),
            )

            val rigFanSettingsEvent = MonitoringEvent.RigFanSettings(fans = rigItem.fans)

            RigControlButton(
                onClick = { onRigControlEvent(rigFanSettingsEvent) },
                text = "FAN SETTINGS",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun RigPowerControlButton(
    onClick: () -> Unit,
    model: RigControlModel,
    modifier: Modifier = Modifier
) {
    val powerStateText = when (model.powerState) {
        RigPowerState.PoweredOn -> "POWER OFF"
        RigPowerState.PoweredOff -> "POWER ON"
        RigPowerState.PoweringOff -> "POWERING OFF"
        RigPowerState.Rebooting -> "POWER OFF"
    }

    val isEnabled = model.powerState.run {
        this == RigPowerState.PoweredOn || this == RigPowerState.PoweredOff
    }

    RigControlButton(
        onClick = onClick,
        text = powerStateText,
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier,
        enabled = isEnabled
    )
}

@Composable
private fun RigRebootControlButton(
    onClick: () -> Unit,
    model: RigControlModel,
    modifier: Modifier = Modifier
) {
    val powerStateText = when (model.powerState) {
        RigPowerState.PoweredOn -> "REBOOT"
        RigPowerState.PoweredOff -> "REBOOT"
        RigPowerState.PoweringOff -> "REBOOT"
        RigPowerState.Rebooting -> "REBOOTING"
    }

    val isEnabled = model.powerState == RigPowerState.PoweredOn

    RigControlButton(
        onClick = onClick,
        text = powerStateText,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier,
        enabled = isEnabled
    )
}

@Composable
private fun RigMiningControlButton(
    onClick: () -> Unit,
    model: RigControlModel,
    modifier: Modifier = Modifier
) {
    val miningStatusText = when (model.miningState) {
        RigMiningState.Started -> "STOP MINING"
        RigMiningState.Stopped -> "START MINING"
        RigMiningState.Starting -> "STARTING"
        RigMiningState.Stopping -> "STOPPING"
    }

    val isEnabled = model.powerState.run {
        val miningState = model.miningState.run {
            this == RigMiningState.Started || this == RigMiningState.Stopped
        }

        this == RigPowerState.PoweredOn && miningState
    }

    RigControlButton(
        onClick = onClick,
        text = miningStatusText,
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier,
        enabled = isEnabled
    )
}

@Composable
private fun RigControlButton(
    onClick: () -> Unit,
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    MNXBorderedButton(
        onClick = onClick,
        modifier = Modifier
            .height(40.dp)
            .then(modifier),
        enabled = enabled,
        color = color
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MNXTypography.bodyLarge
        )
    }
}