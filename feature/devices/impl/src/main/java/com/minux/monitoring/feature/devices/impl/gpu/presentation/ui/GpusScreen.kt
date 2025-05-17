package com.minux.monitoring.feature.devices.impl.gpu.presentation.ui

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
import com.minux.monitoring.feature.devices.impl.gpu.presentation.navigation.GpuFlowRoute
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component.Gpus
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component.GpusError
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component.GpusShimmer
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.component.GpusUiStatePreviewParameterProvider
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model.GpusAction
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model.GpusEvent
import com.minux.monitoring.feature.devices.impl.gpu.presentation.ui.model.GpusUiState
import kotlinx.coroutines.delay

@Composable
internal fun GpusRoute(
    viewModel: GpusViewModel,
    onNavigate: (GpuFlowRoute) -> Unit
) {
    val state by viewModel.gpusUiState.collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)

    GpusScreen(
        gpusUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
    )

    when (action) {
        is GpusAction.OpenGpuSettingsScreen -> {
            val id = (action as GpusAction.OpenGpuSettingsScreen).id
            val name = (action as GpusAction.OpenGpuSettingsScreen).name

            onNavigate(GpuFlowRoute.Settings(gpuId = id, gpuName = name))
        }

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GpusScreen(
    gpusUiState: GpusUiState,
    onEvent: (GpusEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(GpusEvent.FetchGpus)
    }

    Column(modifier = modifier) {
        Text(
            text = "GPUs",
            style = MNXTypography.headlineMedium
        )

        Spacer(modifier = Modifier.height(6.dp))

        SearchTextField(
            query = gpusUiState.searchQuery,
            onQueryChange = { onEvent(GpusEvent.SearchQueryChanged(searchQuery = it)) },
            enabled = !gpusUiState.gpusIsLoading && !gpusUiState.gpus.isNullOrEmpty(),
            placeholder = { Text(text = "Search") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        val isRefreshing = remember { mutableStateOf(false) }

        LaunchedEffect(isRefreshing.value) {
            if (isRefreshing.value) {
                onEvent(GpusEvent.FetchGpus)
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
                    gpusUiState.gpusIsLoading -> GpusShimmer()

                    gpusUiState.filteredGpus == null -> GpusError(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )

                    else -> Gpus(
                        gpus = gpusUiState.filteredGpus,
                        onSettingsClick = { gpuId, gpuName ->
                            onEvent(GpusEvent.Settings(gpuId = gpuId, gpuName = gpuName))
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun GpusScreenPreview(
    @PreviewParameter(GpusUiStatePreviewParameterProvider::class)
    gpusUiState: GpusUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            GpusScreen(
                gpusUiState = gpusUiState,
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