package com.minux.monitoring.feature.monitoring.impl.presentation.ui.component.rig

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXBottomSheet
import com.minux.monitoring.core.designsystem.component.MNXCard
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.fan.FanTemperatureModel
import com.minux.monitoring.feature.monitoring.impl.presentation.model.rig.fan.RigFanItemModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RigFanSettingsBottomSheet(
    showSheet: Boolean,
    onShowSheetChange: (Boolean) -> Unit,
    fans: List<RigFanItemModel>,
    modifier: Modifier = Modifier
) {
    val selectedFan = remember {
        mutableStateOf(
            fans.firstOrNull() ?: RigFanItemModel(
                number = 0,
                power = 0,
                inTemperature = 0,
                outTemperature = 0
            )
        )
    }

    MNXBottomSheet(
        showSheet = showSheet,
        onShowSheetChange = onShowSheetChange,
        modifier = modifier
    ) {
        LazyHorizontalGrid(rows = GridCells.Adaptive(minSize = 30.dp)) {
            items(fans) {
                RigFanItem(
                    model = it,
                    isSelected = selectedFan.value == it,
                    modifier = Modifier.clickable { selectedFan.value = it }
                )
            }
        }

        Row {
            FanTemperatureCard(
                model = FanTemperatureModel(
                    type = "In",
                    value = selectedFan.value.inTemperature
                )
            )

            FanTemperatureCard(
                model = FanTemperatureModel(
                    type = "Out",
                    value = selectedFan.value.outTemperature
                )
            )
        }
    }
}

@Composable
private fun RigFanItem(
    model: RigFanItemModel,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val fanColor = if (isSelected)
            MaterialTheme.colorScheme.secondary
        else
            MaterialTheme.colorScheme.onPrimary

        Text(
            text = "FAN${model.number}",
            color = fanColor
        )

        Text(
            text = "${model.power}%",
            color = fanColor
        )
    }
}

@Composable
private fun FanTemperatureCard(
    model: FanTemperatureModel,
    modifier: Modifier = Modifier
) {
    MNXCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .width(100.dp)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = model.type)

            Text(text = model.value.toString())
        }
    }
}

@Preview
@Composable
private fun RigFanSettingsBottomSheetPreview() {
    MNXTheme {
        RigFanSettingsBottomSheet(
            showSheet = true,
            onShowSheetChange = {},
            fans = listOf(
                RigFanItemModel(
                    number = 1,
                    power = 100,
                    inTemperature = 50,
                    outTemperature = 60
                )
            )
        )
    }
}