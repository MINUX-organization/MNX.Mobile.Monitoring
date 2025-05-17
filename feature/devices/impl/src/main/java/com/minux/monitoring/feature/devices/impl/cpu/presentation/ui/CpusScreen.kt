package com.minux.monitoring.feature.devices.impl.cpu.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.SearchTextField
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component.Cpus
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component.CpusError
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component.CpusShimmer
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.component.CpusUiStatePreviewParameterProvider
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model.CpusEvent
import com.minux.monitoring.feature.devices.impl.cpu.presentation.ui.model.CpusUiState
import kotlinx.coroutines.delay

@Composable
internal fun CpusRoute(viewModel: CpusViewModel) {
    val state by viewModel.cpusUiState.collectAsStateWithLifecycle()

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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CpusScreen(
    cpusUiState: CpusUiState,
    onEvent: (CpusEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(CpusEvent.FetchCpus)
    }

    Column(modifier = modifier) {
        Text(
            text = "CPUs",
            style = MNXTypography.headlineMedium
        )

        Spacer(modifier = Modifier.height(6.dp))

        SearchTextField(
            query = cpusUiState.searchQuery,
            onQueryChange = { onEvent(CpusEvent.SearchQueryChanged(searchQuery = it)) },
            enabled = !cpusUiState.cpusIsLoading && !cpusUiState.cpus.isNullOrEmpty(),
            placeholder = { Text(text = "Search") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        val isRefreshing = remember { mutableStateOf(false) }

        LaunchedEffect(isRefreshing.value) {
            if (isRefreshing.value) {
                onEvent(CpusEvent.FetchCpus)
                delay(200)
                isRefreshing.value = false
            }
        }

        PullToRefreshBox(
            isRefreshing = isRefreshing.value,
            onRefresh = { isRefreshing.value = true },
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                when {
                    cpusUiState.cpusIsLoading -> CpusShimmer()

                    cpusUiState.filteredCpus == null -> CpusError(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )

                    else -> Cpus(
                        cpus = cpusUiState.filteredCpus,
                        modifier = Modifier.weight(1f)
                    )
                }
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