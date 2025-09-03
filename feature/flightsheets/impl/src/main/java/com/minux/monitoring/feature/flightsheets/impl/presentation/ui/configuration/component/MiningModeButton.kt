package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.device.miner.MiningModeModel

@Composable
internal fun MiningModeButton(
    miningModeLabels: List<String>,
    miningModes: List<MiningModeModel>,
    supportedModes: List<MiningModeModel>,
    selectedMode: MiningModeModel,
    onMiningModeChange: (MiningModeModel) -> Unit,
    modifier: Modifier = Modifier
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        miningModes.forEachIndexed { index, item ->
            if (index != 0) Spacer(modifier = Modifier.width(4.dp))

            SegmentedButton(
                selected = selectedMode == item,
                onClick = { onMiningModeChange(item) },
                shape = RoundedCornerShape(4.dp),
                enabled = supportedModes.contains(item),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.primary,
                    activeContentColor = MaterialTheme.colorScheme.onPrimary,
                    activeBorderColor = MaterialTheme.colorScheme.primary,
                    inactiveContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    inactiveContentColor = MaterialTheme.colorScheme.onPrimary,
                    inactiveBorderColor = MaterialTheme.colorScheme.primary,
                    disabledActiveContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    disabledActiveContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                    disabledActiveBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    disabledInactiveContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    disabledInactiveContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                    disabledInactiveBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                ),
                icon = {
                    SegmentedButtonDefaults.Icon(
                        active = selectedMode == item,
                        activeContent = {
                            Icon(
                                painter = painterResource(id = MNXIcons.Check),
                                contentDescription = "Selected"
                            )
                        }
                    )
                },
                label = {
                    Text(
                        text = miningModeLabels[index],
                        style = MNXTypography.titleSmall
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun MiningModeButtonPreview() {
    MNXTheme {
        val miningModes = listOf(MiningModeModel.Single, MiningModeModel.Dual, MiningModeModel.Triple)

        val selectedMiningMode = remember {
            mutableStateOf(miningModes.first())
        }

        MiningModeButton(
            miningModeLabels = listOf("Sample 1", "Sample 2", "Sample 3"),
            miningModes = miningModes,
            supportedModes = listOf(MiningModeModel.Single, MiningModeModel.Dual),
            selectedMode = selectedMiningMode.value,
            onMiningModeChange = { selectedMiningMode.value = it }
        )
    }
}