package com.minux.monitoring.feature.presets.impl.presentation.ui.presets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.ui.SearchAndSortBar
import com.minux.monitoring.feature.presets.impl.presentation.ui.presets.component.DevicePresetItem

@Composable
internal fun PresetsScreen(
    presetsUiState: PresetsUiState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MNXBorderedButton(onClick = {}) {
                Text(
                    text = "Create Preset",
                    fontFamily = grillSansMtFamily,
                    fontSize = 16.sp
                )
            }

            Text(
                text = "Presets",
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = grillSansMtFamily,
                fontSize = 28.sp
            )
        }

        val deviceTypeOptions = listOf("All", "Type 1", "Type 2")
        val selectedDeviceType = remember {
            mutableStateOf(deviceTypeOptions.first())
        }

        val deviceNameQuery = remember {
            mutableStateOf("")
        }

        SearchAndSortBar(
            sortOptions = deviceTypeOptions,
            selectedSortOption = "Sort by ${selectedDeviceType.value}",
            onSelectedSortOptionChange = { selectedDeviceType.value = it },
            searchQuery = deviceNameQuery.value,
            onSearchQueryChange = { deviceNameQuery.value = it },
            modifier = Modifier.padding(top = 10.dp)
        )

        LazyColumn(modifier = Modifier.padding(top = 12.dp)) {
            items(presetsUiState.presets) {
                DevicePresetItem(model = it)

                Spacer(modifier = Modifier.padding(bottom = 10.dp))
            }
        }
    }
}

@Preview
@Composable
internal fun PresetsScreenPreview(
    @PreviewParameter(PresetsUiStatePreviewParameterProvider::class)
    presetsUiState: PresetsUiState
) {
    MNXTheme {
        PresetsScreen(
            presetsUiState = presetsUiState,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

