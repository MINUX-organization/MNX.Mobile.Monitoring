package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.component

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetModel

@Composable
internal fun FlightSheetTargetDetails(
    targets: List<FlightSheetTargetModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(targets) { index, target ->
            if (index != 0) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }

            when (target) {
                is FlightSheetTargetModel.CpuTargetModel -> CpuTargetDetailsItem(model = target)
                is FlightSheetTargetModel.GpuTargetModel -> GpuTargetDetailsItem(model = target)
            }
        }
    }
}

@Composable
private fun CpuTargetDetailsItem(
    model: FlightSheetTargetModel.CpuTargetModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "CPU",
            color = MaterialTheme.colorScheme.primary,
            style = MNXTypography.titleMedium
        )

        FlightSheetTargetDetail(
            name = "Huge Pages",
            value = model.hugePages
        )

        FlightSheetTargetDetail(
            name = "Threads Count",
            value = model.threadsCount
        )

        FlightSheetTargetDetail(
            name = "Additional Args",
            value = model.additionalArguments
        )

        if (model.coinConfigs.size > 1) {
            model.coinConfigs.forEachIndexed { index, coinConfig ->
                FlightSheetTargetDetail(
                    name = "Pool password ${index + 1}",
                    value = coinConfig.poolPassword
                )
            }
        } else {
            FlightSheetTargetDetail(
                name = "Pool password",
                value = model.coinConfigs.firstOrNull()?.poolPassword
            )
        }
    }
}

@Composable
private fun GpuTargetDetailsItem(
    model: FlightSheetTargetModel.GpuTargetModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "GPU",
            color = MaterialTheme.colorScheme.primary,
            style = MNXTypography.titleMedium
        )

        FlightSheetTargetDetail(
            name = "Additional Args",
            value = model.additionalArguments
        )

        model.coinConfigs.forEachIndexed { index, coinConfig ->
            FlightSheetTargetDetail(
                name = "Pool password ${index + 1}",
                value = coinConfig.poolPassword
            )
        }
    }
}

@Composable
private fun FlightSheetTargetDetail(
    name: String,
    value: String?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .basicMarquee(
                iterations = Int.MAX_VALUE,
                velocity = 45.dp
            )
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(end = 4.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = value ?: "Not set",
            modifier = Modifier.padding(start = 4.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}