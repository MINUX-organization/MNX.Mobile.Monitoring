package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.flightsheets.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXExpandableCard
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.flipScale
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.FlightSheetItemModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.target.FlightSheetTargetTypeModel

@Composable
internal fun FlightSheetItem(
    model: FlightSheetItemModel,
    onApplyFlightSheetClick: () -> Unit,
    onEditFlightSheetClick: () -> Unit,
    onRemoveFlightSheetClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }

    MNXExpandableCard(
        expanded = isExpanded.value,
        onExpandedChange = { isExpanded.value = it },
        modifier = modifier,
        borderWidth = 1.dp,
        content = {
            FlightSheetItemContent(
                model = model,
                isExpanded = isExpanded.value,
                onApplyFlightSheetClick = onApplyFlightSheetClick,
                onEditFlightSheetClick = onEditFlightSheetClick,
                onRemoveFlightSheetClick = onRemoveFlightSheetClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
            )
        }
    ) {
        FlightSheetTargetDetails(
            targets = model.targets,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp)
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
        )
    }
}

@Composable
private fun FlightSheetItemContent(
    model: FlightSheetItemModel,
    isExpanded: Boolean,
    onApplyFlightSheetClick: () -> Unit,
    onEditFlightSheetClick: () -> Unit,
    onRemoveFlightSheetClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = model.name,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MNXTypography.titleMedium
            )

            FlightSheetOptionButtons(
                onApplyFlightSheetClick = onApplyFlightSheetClick,
                onEditFlightSheetClick = onEditFlightSheetClick,
                onRemoveFlightSheetClick = onRemoveFlightSheetClick
            )
            
            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                painter = painterResource(id = MNXIcons.DropDown),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .flipScale(state = isExpanded),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val typedTargets = model.targets.associateBy {
            when (it) {
                is FlightSheetTargetModel.CpuTargetModel -> FlightSheetTargetTypeModel.CPU
                is FlightSheetTargetModel.GpuTargetModel -> FlightSheetTargetTypeModel.GPU
            }
        }

        FlightSheetTarget(
            miners = typedTargets.mapValues { it.value.miner },
            coinConfigs = typedTargets.mapValues { it.value.coinConfigs }
        )
    }
}

@Composable
private fun FlightSheetOptionButtons(
    onApplyFlightSheetClick: () -> Unit,
    onEditFlightSheetClick: () -> Unit,
    onRemoveFlightSheetClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FlightSheetOptionButton(
            onClick = onApplyFlightSheetClick,
            icon = painterResource(id = MNXIcons.Check),
            contentDescription = "Apply preset",
            color = MaterialTheme.colorScheme.tertiary
        )

        FlightSheetOptionButton(
            onClick = onEditFlightSheetClick,
            icon = painterResource(id = MNXIcons.Edit),
            contentDescription = "Edit preset"
        )

        FlightSheetOptionButton(
            onClick = onRemoveFlightSheetClick,
            icon = painterResource(id = MNXIcons.Trash),
            contentDescription = "Remove preset",
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
private fun FlightSheetItemPreview(
    @PreviewParameter(FlightSheetItemPreviewParameterProvider::class)
    flightSheetItemModel: FlightSheetItemModel
) {
    MNXTheme {
        FlightSheetItem(
            model = flightSheetItemModel,
            onApplyFlightSheetClick = {},
            onEditFlightSheetClick = {},
            onRemoveFlightSheetClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}