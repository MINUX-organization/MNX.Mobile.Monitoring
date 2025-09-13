package com.minux.monitoring.feature.rigs.impl.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.modifier.shimmerEffect
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.rigs.impl.presentation.model.RigItemModel

@Composable
internal fun Rigs(
    rigs: List<RigItemModel>,
    onStartMiningClick: (rigId: String) -> Unit,
    onStopMiningClick: (rigId: String) -> Unit,
    onRebootClick: (rigId: String) -> Unit,
    onPowerOffClick: (rigId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(rigs, key = { _, item -> item.id }) { index, item ->
            if (index != 0) {
                Spacer(modifier = Modifier.height(8.dp))
            }

            RigItem(
                model = item,
                onStartMiningClick = { onStartMiningClick(item.id) },
                onStopMiningClick = { onStopMiningClick(item.id) },
                onRebootClick = { onRebootClick(item.id) },
                onPowerOffClick = { onPowerOffClick(item.id) }
            )
        }
    }
}

@Composable
internal fun RigsShimmer() {
    Column {
        repeat(8) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
internal fun RigsError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = MNXIcons.MinuxError),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Failed to load rigs",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MNXTypography.titleSmall
        )
    }
}