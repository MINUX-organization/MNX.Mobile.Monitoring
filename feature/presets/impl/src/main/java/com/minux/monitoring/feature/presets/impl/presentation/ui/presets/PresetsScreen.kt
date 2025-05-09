package com.minux.monitoring.feature.presets.impl.presentation.ui.presets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.component.MNXFloatingActionButton
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.SearchTextField
import com.minux.monitoring.feature.presets.impl.presentation.navigation.PresetsFlowRoute
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.PresetGroups
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.PresetGroupsError
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.PresetGroupsShimmer
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.PresetsUiStatePreviewParameterProvider
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.model.PresetsAction
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.model.PresetsEvent
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.model.PresetsUiState
import kotlinx.coroutines.delay

@Composable
internal fun PresetsRoute(
    viewModel: PresetsViewModel,
    onNavigate: (PresetsFlowRoute) -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.uiStates().collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)

    PresetsScreen(
        presetsUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )

    when (action) {
        is PresetsAction.OpenPresetApplyScreen -> {
            val presetId = (action as PresetsAction.OpenPresetApplyScreen).presetId
            val presetName = (action as PresetsAction.OpenPresetApplyScreen).presetName

            onNavigate(
                PresetsFlowRoute.PresetApply(
                    id = presetId,
                    name = presetName
                )
            )
        }

        is PresetsAction.OpenPresetConfigurationScreen -> {
            val configurationMode = (action as PresetsAction.OpenPresetConfigurationScreen).mode
            onNavigate(PresetsFlowRoute.PresetConfiguration(mode = configurationMode))
        }

        PresetsAction.ShowRemovePresetFailedSnackBar -> onShowSnackBar("Remove preset failed")

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PresetsScreen(
    presetsUiState: PresetsUiState,
    onEvent: (PresetsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(PresetsEvent.FetchPresets)
    }

    Scaffold(
        floatingActionButton = {
            MNXFloatingActionButton(onClick = { onEvent(PresetsEvent.CreatePreset) }) {
                Icon(
                    painter = painterResource(id = MNXIcons.Add),
                    contentDescription = "Create preset",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    ) { scaffoldPadding ->
        Column(modifier = modifier.padding(scaffoldPadding)) {
            Text(
                text = "Presets",
                color = MaterialTheme.colorScheme.onBackground,
                style = MNXTypography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            SearchTextField(
                query = presetsUiState.searchQuery,
                onQueryChange = { onEvent(PresetsEvent.SearchQueryChanged(searchQuery = it)) },
                enabled = !presetsUiState.presetGroupsIsLoading && !presetsUiState.presetGroups.isNullOrEmpty(),
                placeholder = { Text(text = "Search") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            val isRefreshing = remember { mutableStateOf(false) }

            LaunchedEffect(isRefreshing.value) {
                if (isRefreshing.value) {
                    onEvent(PresetsEvent.FetchPresets)
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
                        presetsUiState.presetGroupsIsLoading -> PresetGroupsShimmer()

                        presetsUiState.filteredPresetGroups == null -> PresetGroupsError(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        )

                        else -> PresetGroups(
                            presetGroups = presetsUiState.filteredPresetGroups,
                            onApplyPresetClick = { id, name ->
                                onEvent(PresetsEvent.ApplyPreset(presetId = id, presetName = name))
                            },
                            onEditPresetClick = { id, deviceName ->
                                onEvent(
                                    PresetsEvent.ChangePreset(
                                        presetId = id,
                                        deviceName = deviceName
                                    )
                                )
                            },
                            onRemovePresetClick = { id -> onEvent(PresetsEvent.RemovePreset(presetId = id)) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PresetsScreenPreview(
    @PreviewParameter(PresetsUiStatePreviewParameterProvider::class)
    presetsUiState: PresetsUiState
) {
    MNXTheme {
        PresetsScreen(
            presetsUiState = presetsUiState,
            onEvent = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

