package com.minux.monitoring.feature.rigs.impl.presentation.ui.component

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import com.minux.monitoring.core.ui.IsOnlineIndicator
import com.minux.monitoring.feature.rigs.impl.presentation.model.RigItemModel

@Composable
internal fun RigItem(
    model: RigItemModel,
    onStartMiningClick: () -> Unit,
    onStopMiningClick: () -> Unit,
    onRebootClick: () -> Unit,
    onPowerOffClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isExpanded = remember { mutableStateOf(false) }

    MNXExpandableCard(
        expanded = isExpanded.value,
        onExpandedChange = { isExpanded.value = it },
        modifier = modifier,
        borderWidth = 1.dp,
        content = {
            RigItemContent(
                model = model,
                isExpanded = isExpanded.value,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 20.dp)
            )
        }
    ) {
        RigLifecycleButtons(
            miningStatusModel = model.miningStatus,
            powerStatusModel = model.powerStatus,
            onStartMiningClick = onStartMiningClick,
            onStopMiningClick = onStopMiningClick,
            onRebootClick = onRebootClick,
            onPowerOffClick = onPowerOffClick,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
private fun RigItemContent(
    model: RigItemModel,
    isExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IsOnlineIndicator(
            color = if (model.isOnline)
                MaterialTheme.colorScheme.tertiary
            else
                MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = model.name,
            modifier = Modifier
                .weight(1f)
                .basicMarquee(),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MNXTypography.bodyLarge
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            painter = painterResource(id = MNXIcons.DropDown),
            contentDescription = null,
            modifier = Modifier.flipScale(state = isExpanded),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun RigItemPreview(
    @PreviewParameter(RigItemPreviewParameterProvider::class)
    rigItemModel: RigItemModel
) {
    MNXTheme {
        RigItem(
            model = rigItemModel,
            onStartMiningClick = {},
            onStopMiningClick = {},
            onRebootClick = {},
            onPowerOffClick = {}
        )
    }
}