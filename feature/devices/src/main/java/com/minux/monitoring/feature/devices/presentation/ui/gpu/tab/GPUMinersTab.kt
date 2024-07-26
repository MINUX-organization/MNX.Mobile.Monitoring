package com.minux.monitoring.feature.devices.presentation.ui.gpu.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.modifier.BorderSide
import com.minux.monitoring.core.designsystem.modifier.BorderSides
import com.minux.monitoring.core.designsystem.modifier.selectiveBorder
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.devices.presentation.deviceTextStyle
import com.minux.monitoring.feature.devices.presentation.model.DeviceMinerModel

@Composable
internal fun GPUMinersTab(
    minerItems: List<DeviceMinerModel>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        GPUMinersHeader(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .selectiveBorder(
                    sides = BorderSides(
                        bottom = BorderSide.Bottom(width = 1.dp)
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
                .padding(horizontal = 10.dp)
        )

        LazyColumn(
            modifier = Modifier
                .padding(
                    horizontal = 10.dp,
                    vertical = 2.dp
                )
        ) {
            items(minerItems) { item ->
                GPUMinerItem(
                    model = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun GPUMinersHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = "Miner",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = deviceTextStyle
        )

        Spacer(modifier = Modifier.weight(1f))

        VerticalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = "Card ID",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = deviceTextStyle
        )
    }
}

@Composable
private fun GPUMinerItem(
    model: DeviceMinerModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = model.miner,
            style = deviceTextStyle
        )
        Text(
            text = model.cardId.toString(),
            style = deviceTextStyle
        )
    }
}

@Preview
@Composable
internal fun GPUMinersTabPreview() {
    MNXTheme {
        GPUMinersTab(
            modifier = Modifier.heightIn(max = 70.dp),
            minerItems = listOf(
                DeviceMinerModel(
                    miner = "lolminer",
                    cardId = 2
                ),
                DeviceMinerModel(
                    miner = "lolminer",
                    cardId = 4
                ),
                DeviceMinerModel(
                    miner = "lolminer",
                    cardId = 0
                )
            )
        )
    }
}