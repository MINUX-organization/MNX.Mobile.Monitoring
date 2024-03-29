package com.minux.monitoring.feature.monitoring.ui

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXButton
import com.minux.monitoring.feature.monitoring.MiningStatus
import com.minux.monitoring.feature.monitoring.commonTextStyle

@Composable
fun RigControls(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    miningStatus: MiningStatus,
    onPowerOffRig: () -> Unit,
    onRebootRig: () -> Unit,
    onStartMiningOnRig: () -> Unit,
    onStopMiningOnRig: () -> Unit
) {
    Row(modifier = modifier) {
        RigMiningControlButton(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            snackbarHostState = snackbarHostState,
            miningStatus = miningStatus,
            onStartMiningOnRig = onStartMiningOnRig,
            onStopMiningOnRig = onStopMiningOnRig
        )

        RigControlButton(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            onClick = onPowerOffRig,
            text = "POWER OFF",
            color = MaterialTheme.colorScheme.secondary
        )
    }

    Row(
        modifier = modifier
            .padding(
                top = 12.dp,
                bottom = 6.dp
            )
    ) {
        RigControlButton(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            onClick = onRebootRig,
            text = "REBOOT",
            color = MaterialTheme.colorScheme.primary
        )

        RigControlButton(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            onClick = onRebootRig,
            text = "REBOOT IN 30s",
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun RigMiningControlButton(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    miningStatus: MiningStatus,
    onStartMiningOnRig: () -> Unit,
    onStopMiningOnRig: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var (miningStatusText, miningStatusColor) = Pair("", Color.White)

    when (miningStatus) {
        MiningStatus.Started -> {
            miningStatusText = "STOP MINING"
            miningStatusColor = MaterialTheme.colorScheme.secondary
        }

        MiningStatus.StartingFailure -> {
            LaunchedEffect(coroutineScope) {
                snackbarHostState.showSnackbar("Rig starting failure")
            }
        }

        MiningStatus.Starting -> {
            miningStatusText = "STARTING..."
            miningStatusColor = MaterialTheme.colorScheme.primary
        }

        MiningStatus.Stopped -> {
            miningStatusText = "START MINING"
            miningStatusColor = MaterialTheme.colorScheme.primary
        }

        MiningStatus.StoppingFailure -> {
            LaunchedEffect(coroutineScope) {
                snackbarHostState.showSnackbar("Rig stopping failure")
            }
        }

        MiningStatus.Stopping -> {
            miningStatusText = "STOPPING..."
            miningStatusColor = MaterialTheme.colorScheme.secondary
        }
    }

    RigControlButton(
        modifier = modifier,
        onClick = {
            if (miningStatus == MiningStatus.Started) {
                onStopMiningOnRig()
            }

            if (miningStatus == MiningStatus.Stopped) {
                onStartMiningOnRig()
            }
        },
        text = miningStatusText,
        color = miningStatusColor
    )
}

@Composable
private fun RigControlButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    color: Color
) {
    MNXButton(
        modifier = Modifier
            .height(40.dp)
            .then(modifier),
        onClick = onClick,
        color = color
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = commonTextStyle
            )
        }
    }
}