package com.minux.monitoring.feature.presets.impl.presentation.ui.presets

import androidx.lifecycle.viewModelScope
import com.minux.monitoring.core.base.BaseViewModel
import com.minux.monitoring.feature.presets.impl.data.model.PresetRemoveDto
import com.minux.monitoring.feature.presets.impl.data.repository.PresetRepository
import com.minux.monitoring.feature.presets.impl.presentation.mapper.toDevicePresetGroupItemModel
import com.minux.monitoring.feature.presets.impl.presentation.model.ConfigurationMode
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.model.PresetsAction
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.model.PresetsEvent
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.model.PresetsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class PresetsViewModel @Inject constructor(
    private val presetRepository: PresetRepository
) : BaseViewModel<PresetsUiState, PresetsAction, PresetsEvent>(initialState = PresetsUiState()) {

    override fun onEvent(uiEvent: PresetsEvent) {
        when (uiEvent) {
            PresetsEvent.FetchPresets -> fetchPresets()

            is PresetsEvent.SearchQueryChanged -> searchQueryChanged(query = uiEvent.searchQuery)

            PresetsEvent.CreatePreset -> {
                uiAction = PresetsAction.OpenPresetConfigurationScreen(mode = ConfigurationMode.Create)
            }

            is PresetsEvent.ApplyPreset -> {
                uiAction = PresetsAction.OpenPresetApplyScreen(
                    presetId = uiEvent.presetId,
                    presetName = uiEvent.presetName
                )
            }

            is PresetsEvent.ChangePreset -> {
                val editPresetMode = ConfigurationMode.Edit(
                    presetId = uiEvent.presetId,
                    deviceName = uiEvent.deviceName
                )

                uiAction = PresetsAction.OpenPresetConfigurationScreen(mode = editPresetMode)
            }

            is PresetsEvent.RemovePreset -> removePreset(presetId = uiEvent.presetId)
        }
    }

    private fun fetchPresets() {
        presetRepository.getAllPresetsGroupedByGpus()
            .onStart { uiState = uiState.copy(presetGroupsIsLoading = true) }
            .onEach { result ->
                val presets = result.getOrNull()?.map { it.toDevicePresetGroupItemModel() }

                uiState = uiState.copy(
                    presetGroupsIsLoading = false,
                    presetGroups = presets,
                    filteredPresetGroups = presets
                )
            }
            .launchIn(viewModelScope)
    }

    private fun searchQueryChanged(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                if (uiState.presetGroups != uiState.filteredPresetGroups)
                    uiState = uiState.copy(
                        searchQuery = query,
                        filteredPresetGroups = uiState.presetGroups
                    )

                return@launch
            }

            val filteredGroups = uiState.presetGroups?.map { presetGroup ->
                val filteredPresets = presetGroup.presets.filter { it.info.presetName == query }
                presetGroup.copy(presets = filteredPresets)
            }

            uiState = uiState.copy(
                searchQuery = query,
                filteredPresetGroups = filteredGroups
            )
        }
    }

    private fun removePreset(presetId: String) {
        val presetRemoveDto = PresetRemoveDto(id = presetId)

        presetRepository.removePreset(presetRemove = presetRemoveDto)
            .onEach { result ->
                result.onSuccess {
                    fetchPresets()
                }.onFailure {
                    uiAction = PresetsAction.ShowRemovePresetFailedSnackBar
                }
            }
            .launchIn(viewModelScope)
    }
}