package com.minux.monitoring.feature.monitoring.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.feature.monitoring.RigMiningStatus
import com.minux.monitoring.feature.monitoring.RigPowerState
import com.minux.monitoring.feature.monitoring.commonTextStyle

@Composable
internal fun RigControls(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    rigPowerState: RigPowerState,
    rigMiningStatus: RigMiningStatus,
    onPowerOffRig: () -> Unit,
    onRebootRig: () -> Unit,
    onStartMiningOnRig: () -> Unit,
    onStopMiningOnRig: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RigMiningControlButton(
                modifier = Modifier.weight(1f),
                snackbarHostState = snackbarHostState,
                rigPowerState = rigPowerState,
                rigMiningStatus = rigMiningStatus,
                onStartMiningOnRig = onStartMiningOnRig,
                onStopMiningOnRig = onStopMiningOnRig
            )

            RigPowerControlButton(
                modifier = Modifier.weight(1f),
                rigPowerState = rigPowerState,
                snackbarHostState = snackbarHostState,
                onPowerOffRig = onPowerOffRig
            )
        }

        Row(
            modifier = modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RigRebootControlButton(
                modifier = Modifier.weight(1f),
                rigPowerState = rigPowerState,
                snackbarHostState = snackbarHostState,
                onRebootRig = onRebootRig
            )

            RigControlButton(
                modifier = Modifier.weight(1f),
                onClick = onRebootRig,
                text = "FAN SETTINGS",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun RigPowerControlButton(
    rigPowerState: RigPowerState,
    snackbarHostState: SnackbarHostState,
    onPowerOffRig: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    val isEnabled = rigPowerState is RigPowerState.PoweredOn ||
            rigPowerState is RigPowerState.PoweredOff
    var powerStateText = ""

    when (rigPowerState) {
        RigPowerState.PoweredOn -> {
            powerStateText = "POWER OFF"
        }

        RigPowerState.PoweredOff -> {
            powerStateText = "POWER ON"
        }

        RigPowerState.PoweringOff -> {
            powerStateText = "POWERING OFF"
        }

        RigPowerState.Rebooting -> {
            powerStateText = "POWER OFF"
        }

        is RigPowerState.Error -> {
            LaunchedEffect(coroutineScope) {
                snackbarHostState.showSnackbar(
                    message = rigPowerState.message ?: "Error was occurred"
                )
            }
        }
    }

    RigControlButton(
        modifier = modifier,
        onClick = onPowerOffRig,
        text = powerStateText,
        color = MaterialTheme.colorScheme.secondary,
        enabled = isEnabled
    )
}

@Composable
private fun RigRebootControlButton(
    rigPowerState: RigPowerState,
    snackbarHostState: SnackbarHostState,
    onRebootRig: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    val isEnabled = rigPowerState is RigPowerState.PoweredOn
    var powerStateText = ""

    when (rigPowerState) {
        RigPowerState.PoweredOn -> {
            powerStateText = "REBOOT"
        }

        RigPowerState.PoweredOff -> {
            powerStateText = "REBOOT"
        }

        RigPowerState.PoweringOff -> {
            powerStateText = "REBOOT"
        }

        RigPowerState.Rebooting -> {
            powerStateText = "REBOOTING"
        }

        is RigPowerState.Error -> {
            LaunchedEffect(coroutineScope) {
                snackbarHostState.showSnackbar(
                    message = rigPowerState.message ?: "Error was occurred"
                )
            }
        }
    }

    RigControlButton(
        modifier = modifier,
        onClick = onRebootRig,
        text = powerStateText,
        color = MaterialTheme.colorScheme.primary,
        enabled = isEnabled
    )
}

@Composable
private fun RigMiningControlButton(
    rigPowerState: RigPowerState,
    rigMiningStatus: RigMiningStatus,
    snackbarHostState: SnackbarHostState,
    onStartMiningOnRig: () -> Unit,
    onStopMiningOnRig: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    val isEnabled = rigPowerState is RigPowerState.PoweredOn &&
            (rigMiningStatus is RigMiningStatus.Started ||
                    rigMiningStatus is RigMiningStatus.Stopped)

    var miningStatusText = ""

    when (rigMiningStatus) {
        RigMiningStatus.Started -> {
            miningStatusText = "STOP MINING"
        }

        RigMiningStatus.Stopped -> {
            miningStatusText = "START MINING"
        }

        RigMiningStatus.Starting -> {
            miningStatusText = "STARTING"
        }

        RigMiningStatus.Stopping -> {
            miningStatusText = "STOPPING"
        }

        is RigMiningStatus.Error -> {
            LaunchedEffect(coroutineScope) {
                snackbarHostState.showSnackbar(
                    message = rigMiningStatus.message ?: "Error was occurred"
                )
            }
        }
    }

    RigControlButton(
        modifier = modifier,
        onClick = {
            if (rigMiningStatus == RigMiningStatus.Started) {
                onStopMiningOnRig()
            }

            if (rigMiningStatus == RigMiningStatus.Stopped) {
                onStartMiningOnRig()
            }
        },
        text = miningStatusText,
        color = MaterialTheme.colorScheme.secondary,
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
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = commonTextStyle
            )
        }
    }
}