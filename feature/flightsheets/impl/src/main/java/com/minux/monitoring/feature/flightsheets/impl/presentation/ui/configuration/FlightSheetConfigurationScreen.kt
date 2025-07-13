package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.BackButton
import com.minux.monitoring.feature.flightsheets.impl.presentation.mapper.toMessage
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.ConfigurationMode
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component.FlightSheetConfigurationUiStatePreviewParameterProvider
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component.FlightSheetInputCard
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.model.FlightSheetConfigurationAction
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.model.FlightSheetConfigurationEvent
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.model.FlightSheetConfigurationUiState

@Composable
internal fun FlightSheetConfigurationRoute(
    viewModel: FlightSheetConfigurationViewModel,
    configurationMode: ConfigurationMode,
    onNavigateUp: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.uiStates().collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)

    FlightSheetConfigurationScreen(
        flightSheetConfigurationUiState = state,
        mode = configurationMode,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )

    when (action) {
        FlightSheetConfigurationAction.OpenPreviousScreen -> onNavigateUp()

        FlightSheetConfigurationAction.ShowCreateFlightSheetFailedSnackBar -> {
            onShowSnackBar("Create flight sheet failed")
        }

        FlightSheetConfigurationAction.ShowChangeFlightSheetFailedSnackBar -> {
            onShowSnackBar("Change flight sheet failed")
        }

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@Composable
private fun FlightSheetConfigurationScreen(
    flightSheetConfigurationUiState: FlightSheetConfigurationUiState,
    mode: ConfigurationMode,
    onEvent: (FlightSheetConfigurationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(FlightSheetConfigurationEvent.FetchFlightSheetParameters(mode = mode))
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(onClick = { onEvent(FlightSheetConfigurationEvent.Back) })

            Text(
                text = mode.toMessage(),
                style = MNXTypography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        FlightSheetInputCard(
            model = flightSheetConfigurationUiState.flightSheet,
            targetTypes = flightSheetConfigurationUiState.targetTypes,
            minersIsLoading = flightSheetConfigurationUiState.minersIsLoading,
            miners = flightSheetConfigurationUiState.miners,
            miningModes = flightSheetConfigurationUiState.miningModes,
            poolsIsLoading = flightSheetConfigurationUiState.poolsIsLoading,
            pools = flightSheetConfigurationUiState.pools,
            walletsIsLoading = flightSheetConfigurationUiState.walletsIsLoading,
            wallets = flightSheetConfigurationUiState.wallets,
            onNameChange = { onEvent(FlightSheetConfigurationEvent.NameChanged(name = it)) },
            onTargetTypeChange = {
                onEvent(FlightSheetConfigurationEvent.TargetTypeChanged(targetType = it))
            },
            onCpuMinerChange = { onEvent(FlightSheetConfigurationEvent.CpuMinerChanged(miner = it)) },
            onCpuHugePagesChange = {
                onEvent(FlightSheetConfigurationEvent.CpuHugePagesChanged(hugePages = it))
            },
            onCpuThreadsCountChange = {
                onEvent(FlightSheetConfigurationEvent.CpuThreadsCountChanged(threadsCount = it))
            },
            onCpuPoolChange = { pool, index ->
                onEvent(
                    FlightSheetConfigurationEvent.CpuPoolChanged(
                        pool = pool,
                        coinConfigIndex = index
                    )
                )
            },
            onCpuWalletChange = { wallet, index ->
                onEvent(
                    FlightSheetConfigurationEvent.CpuWalletChanged(
                        wallet = wallet,
                        coinConfigIndex = index
                    )
                )
            },
            onCpuPoolPasswordChange = { poolPassword, index ->
                onEvent(
                    FlightSheetConfigurationEvent.CpuPoolPasswordChanged(
                        poolPassword = poolPassword,
                        coinConfigIndex = index
                    )
                )
            },
            onCpuMinerAdditionalArgumentsChange = {
                onEvent(
                    FlightSheetConfigurationEvent
                        .CpuMinerAdditionalArgumentsChanged(minerAdditionalArguments = it)
                )
            },
            onCpuMinerConfigFileChange = {
                onEvent(FlightSheetConfigurationEvent.CpuMinerConfigFileChanged(minerConfigFile = it))
            },
            onGpuMinerChange = { onEvent(FlightSheetConfigurationEvent.GpuMinerChanged(miner = it)) },
            onGpuMinerMiningModeChange = {
                onEvent(FlightSheetConfigurationEvent.GpuMinerMiningModeChanged(minerMiningMode = it))
            },
            onGpuPoolChange = { pool, index ->
                onEvent(
                    FlightSheetConfigurationEvent.GpuPoolChanged(
                        pool = pool,
                        coinConfigIndex = index
                    )
                )
            },
            onGpuWalletChange = { wallet, index ->
                onEvent(
                    FlightSheetConfigurationEvent.GpuWalletChanged(
                        wallet = wallet,
                        coinConfigIndex = index
                    )
                )
            },
            onGpuPoolPasswordChange = { poolPassword, index ->
                onEvent(
                    FlightSheetConfigurationEvent.GpuPoolPasswordChanged(
                        poolPassword = poolPassword,
                        coinConfigIndex = index
                    )
                )
            },
            onGpuMinerAdditionalArgumentsChange = {
                onEvent(
                    FlightSheetConfigurationEvent
                        .GpuMinerAdditionalArgumentsChanged(minerAdditionalArguments = it)
                )
            },
            onGpuMinerConfigFileChange = {
                onEvent(FlightSheetConfigurationEvent.GpuMinerConfigFileChanged(minerConfigFile = it))
            },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        MNXBorderedButton(
            onClick = { onEvent(FlightSheetConfigurationEvent.Confirm(mode = mode)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            enabled = flightSheetConfigurationUiState.flightSheet.isNameValid,
            color = MaterialTheme.colorScheme.tertiary
        ) {
            Text(
                text = "Confirm",
                style = MNXTypography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
private fun FlightSheetConfigurationScreenPreview(
    @PreviewParameter(FlightSheetConfigurationUiStatePreviewParameterProvider::class)
    flightSheetConfigurationUiState: FlightSheetConfigurationUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            FlightSheetConfigurationScreen(
                flightSheetConfigurationUiState = flightSheetConfigurationUiState,
                mode = ConfigurationMode.Create,
                onEvent = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}