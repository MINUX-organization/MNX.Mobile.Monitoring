package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.DeviceMinerModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningCoinConfigModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetTypeModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.ui.common.MinerSupportedDevicesIcons

@Composable
internal fun FlightSheetTarget(
    miners: Map<FlightSheetTargetTypeModel, DeviceMinerModel?>,
    coinConfigs: Map<FlightSheetTargetTypeModel, List<MiningCoinConfigModel>>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            coinConfigs.forEach {
                MiningCoinConfigItem(
                    type = it.key,
                    coinConfigs = it.value
                )
            }
        }

        Column {
            miners.forEach { miner ->
                miner.value?.let {
                    DeviceMinerItem(
                        type = miner.key,
                        minerModel = it
                    )
                }
            }
        }
    }
}

@Composable
private fun DeviceMinerItem(
    type: FlightSheetTargetTypeModel,
    minerModel: DeviceMinerModel,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        itemVerticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${type.name} Miner",
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = buildAnnotatedString {
                append(text = minerModel.name ?: "N/A")

                minerModel.version?.let {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimaryContainer)) {
                        append(text = " - $it")
                    }
                }
            },
            color = MaterialTheme.colorScheme.onPrimary
        )

        MinerSupportedDevicesIcons(
            devices = minerModel.supportedDevices
        )
    }
}

@Composable
private fun MiningCoinConfigItem(
    type: FlightSheetTargetTypeModel,
    coinConfigs: List<MiningCoinConfigModel>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = type.name,
            color = MaterialTheme.colorScheme.primary
        )

        coinConfigs.forEachIndexed { index, coinConfig ->
            Text(
                text = buildAnnotatedString {
                    append(text = "Coin")

                    if (type == FlightSheetTargetTypeModel.GPU) append(text = "-${index + 1}")

                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary)) {
                        append(text = " ${coinConfig.wallet?.coin ?: "N/A"}")
                    }
                },
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = buildAnnotatedString {
                    append(text = "Pool")

                    if (type == FlightSheetTargetTypeModel.GPU) append(text = "-${index + 1}")

                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary)) {
                        append(text = " ${coinConfig.pool?.name ?: "N/A"}")
                    }

                },
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = buildAnnotatedString {
                    append(text = "Wallet")

                    if (type == FlightSheetTargetTypeModel.GPU) append(text = "-${index + 1}")

                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary)) {
                        append(text = " ${coinConfig.wallet?.name ?: "N/A"}")
                    }
                },
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}