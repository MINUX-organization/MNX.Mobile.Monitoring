package com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.component

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
import com.minux.monitoring.feature.cryptos.impl.presentation.model.PoolInputModel
import com.minux.monitoring.feature.cryptos.impl.presentation.model.PoolItemModel
import com.minux.monitoring.feature.cryptos.impl.presentation.ui.pools.model.PoolsEvent

internal fun LazyGridScope.poolsGridItems(
    item: PoolItemModel,
    itemPadding: PaddingValues,
    onChangePool: (PoolsEvent.ChangePool) -> Unit,
    onRemovePool: (PoolsEvent.RemovePool) -> Unit
) {
    item {
        Text(
            modifier = Modifier.padding(paddingValues = itemPadding),
            text = item.cryptocurrency
        )
    }

    item {
        Text(
            modifier = Modifier
                .padding(paddingValues = itemPadding)
                .padding(horizontal = 2.dp),
            text = item.domain
        )
    }

    item {
        Text(
            modifier = Modifier.padding(paddingValues = itemPadding),
            text = item.port.toString()
        )
    }

    item {
        PoolControlsGridItem(
            item = item,
            onChangePool = onChangePool,
            onRemovePool = onRemovePool,
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}

@Composable
private fun PoolControlsGridItem(
    item: PoolItemModel,
    onChangePool: (PoolsEvent.ChangePool) -> Unit,
    onRemovePool: (PoolsEvent.RemovePool) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val changePoolEvent = PoolsEvent.ChangePool(
            id = item.id,
            pool = PoolInputModel(
                domain = item.domain,
                port = item.port,
                cryptocurrencyId = item.cryptocurrency
            )
        )

        Icon(
            painter = painterResource(id = MNXIcons.Edit),
            contentDescription = "Update pool",
            modifier = Modifier
                .size(width = 20.dp, height = 20.dp)
                .clickable { onChangePool(changePoolEvent) }
        )

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Remove pool",
            modifier = Modifier
                .size(width = 25.dp, height = 25.dp)
                .clickable { onRemovePool(PoolsEvent.RemovePool(id = item.id)) },
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}