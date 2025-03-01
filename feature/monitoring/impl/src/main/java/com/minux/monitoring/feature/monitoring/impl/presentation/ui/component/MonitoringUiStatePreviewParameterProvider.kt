package com.minux.monitoring.feature.monitoring.impl.presentation.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.monitoring.impl.presentation.model.MetricsOverviewModel
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.model.MonitoringUiState
import com.minux.monitoring.feature.monitoring.impl.presentation.ui.component.rig.RigItemPreviewParameterProvider

internal class MonitoringUiStatePreviewParameterProvider : PreviewParameterProvider<MonitoringUiState> {
    private val rigItemPreviewParameterProvider = RigItemPreviewParameterProvider()
    private val rigs = rigItemPreviewParameterProvider.values.toList()

    override val values: Sequence<MonitoringUiState>
        get() = sequenceOf(
            MonitoringUiState(isLoading = true),
            MonitoringUiState(isError = true),
            MonitoringUiState(),
            MonitoringUiState(
                metricsOverview = MetricsOverviewModel(
                    rigs = 512,
                    power = 312,
                    powerUnit = "W",
                    accepted = 100000,
                    rejected = 100000
                ),
                coinsStatistics = rigs.first().coins,
                rigs = rigs,
                selectedRigFanConfiguration = rigs.first().fans
            )
        )
}