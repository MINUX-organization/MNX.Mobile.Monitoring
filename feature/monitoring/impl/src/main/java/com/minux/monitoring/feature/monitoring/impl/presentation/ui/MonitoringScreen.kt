package com.minux.monitoring.feature.monitoring.impl.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.monitoring.impl.presentation.model.CoinStatisticsItemModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.MetricsOverviewModel
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.component.CoinStatisticsGrid
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.component.MetricsOverviewCard
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.component.MonitoringUiStatePreviewParameterProvider
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.component.rig.RigFanSettingsBottomSheet
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.component.rig.RigsContainer
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.model.MonitoringAction
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.model.MonitoringEvent
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.model.MonitoringUiState

@Composable
internal fun MonitoringRoute(
    viewModel: MonitoringViewModel,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.uiStates().collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)
    val isRigFanSettingsBottomSheetShow = rememberSaveable { mutableStateOf(false) }

    MonitoringScreen(
        monitoringUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .padding(
                top = 12.dp,
                bottom = 2.dp
            )
    )

    RigFanSettingsBottomSheet(
        showSheet = isRigFanSettingsBottomSheetShow.value,
        onShowSheetChange = { isRigFanSettingsBottomSheetShow.value = it },
        fans = state.selectedRigFanConfiguration
    )

    when (action) {
        MonitoringAction.OpenRigFanSettingsBottomSheet -> {
            isRigFanSettingsBottomSheetShow.value = true
        }

        is MonitoringAction.ShowPowerOffRigFailedSnackBar -> onShowSnackBar("")

        is MonitoringAction.ShowRebootRigFailedSnackBar -> onShowSnackBar("")

        is MonitoringAction.ShowStartMiningOnRigFailedSnackBar -> onShowSnackBar("")

        is MonitoringAction.ShowStopMiningOnRigFailedSnackBar -> onShowSnackBar("")

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@Composable
private fun MonitoringScreen(
    monitoringUiState: MonitoringUiState,
    onEvent: (MonitoringEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(MonitoringEvent.Refresh)
    }

    Column(modifier = modifier) {
        TotalRigsData(
            metricsOverview = monitoringUiState.metricsOverview,
            coinStatisticsItems = monitoringUiState.coinsStatistics,
            placeholder = {
                Box(
                    modifier = Modifier.padding(vertical = 3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (monitoringUiState.isLoading) "..." else "N/A",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MNXTypography.bodyLarge
                    )
                }
            }
        )

        RigsContainer(
            uiState = monitoringUiState,
            onEvent = onEvent
        )
    }
}

@Composable
private fun TotalRigsData(
    metricsOverview: MetricsOverviewModel?,
    coinStatisticsItems: List<CoinStatisticsItemModel>,
    placeholder: (@Composable () -> Unit)? = null
) {
    MetricsOverviewCard(
        model = metricsOverview,
        modifier = Modifier.fillMaxWidth(),
        placeholder = placeholder
    )

    CoinStatisticsGrid(
        headers = listOf("Coin", "Algorithm", "Hashrate", "Accepted", "Rejected"),
        items = coinStatisticsItems,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 180.dp)
            .padding(top = 6.dp),
        itemsPlaceholder = placeholder
    )
}

@Preview
@Composable
private fun MonitoringScreenPreview(
    @PreviewParameter(MonitoringUiStatePreviewParameterProvider::class)
    monitoringUiState: MonitoringUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MonitoringScreen(
                monitoringUiState = monitoringUiState,
                onEvent = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .padding(top = 12.dp)
            )
        }
    }
}