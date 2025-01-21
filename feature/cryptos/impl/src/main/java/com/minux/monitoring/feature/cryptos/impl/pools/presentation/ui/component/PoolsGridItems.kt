package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsEvent

internal fun LazyGridScope.poolsGridItems(
    item: PoolItemModel,
    itemPadding: PaddingValues,
    onSelectPool: (PoolsEvent.SelectPool) -> Unit,
    onRemovePool: (PoolsEvent.RemovePool) -> Unit
) {
    item {
        Text(
            text = item.cryptocurrency,
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        Text(
            text = item.domain,
            modifier = Modifier
                .padding(paddingValues = itemPadding)
                .padding(horizontal = 2.dp)
        )
    }

    item {
        Text(
            text = item.port.toString(),
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        PoolControlsGridItem(
            item = item,
            onSelectPool = onSelectPool,
            onRemovePool = onRemovePool,
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}

@Composable
private fun PoolControlsGridItem(
    item: PoolItemModel,
    onSelectPool: (PoolsEvent.SelectPool) -> Unit,
    onRemovePool: (PoolsEvent.RemovePool) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = MNXIcons.Edit),
            contentDescription = "Update pool",
            modifier = Modifier
                .size(20.dp)
                .clickable { onSelectPool(PoolsEvent.SelectPool(pool = item)) }
        )

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Remove pool",
            modifier = Modifier
                .size(25.dp)
                .clickable { onRemovePool(PoolsEvent.RemovePool(id = item.id)) },
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}