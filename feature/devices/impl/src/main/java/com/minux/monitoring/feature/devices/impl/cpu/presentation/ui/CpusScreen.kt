package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.FiltersButton
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component.CpuItem
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component.CpusUiStatePreviewParameterProvider
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model.CpusAction
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model.CpusEvent
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model.CpusUiState

@Composable
internal fun CpusRoute(viewModel: CpusViewModel) {
    val state by viewModel.cpusUiState.collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)

    CpusScreen(
        cpusUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
    )

    when (action) {
        CpusAction.OpenFiltersBottomSheet -> TODO()

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@Composable
private fun CpusScreen(
    cpusUiState: CpusUiState,
    onEvent: (CpusEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "CPUs",
                style = MNXTypography.headlineMedium
            )

            FiltersButton(onClick = { onEvent(CpusEvent.Filters) })
        }

        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(cpusUiState.cpus, key = { it.id }) {
                CpuItem(
                    model = it,
                    onSettingsClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
private fun CpusScreenPreview(
    @PreviewParameter(CpusUiStatePreviewParameterProvider::class)
    cpusUiState: CpusUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CpusScreen(
                cpusUiState = cpusUiState,
                onEvent = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
            )
        }
    }
}