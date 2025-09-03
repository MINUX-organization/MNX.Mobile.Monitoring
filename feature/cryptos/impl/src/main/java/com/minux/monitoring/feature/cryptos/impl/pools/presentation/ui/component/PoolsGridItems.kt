package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolItemModel

internal fun LazyGridScope.poolsGridItems(
    item: PoolItemModel,
    itemPadding: PaddingValues,
    onChangePoolClick: (PoolItemModel) -> Unit,
    onRemovePoolClick: (String) -> Unit
) {
    item {
        Text(
            text = item.domain ?: "N/A",
            modifier = Modifier
                .padding(paddingValues = itemPadding)
                .padding(end = 2.dp)
        )
    }

    item {
        Text(
            text = item.port.toString(),
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        Text(
            text = item.cryptocurrency ?: "N/A",
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        PoolControlsGridItem(
            item = item,
            onChangePoolClick = onChangePoolClick,
            onRemovePoolClick = onRemovePoolClick
        )
    }
}

@Composable
private fun PoolControlsGridItem(
    item: PoolItemModel,
    onChangePoolClick: (PoolItemModel) -> Unit,
    onRemovePoolClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onChangePoolClick(item) }) {
            Icon(
                painter = painterResource(id = MNXIcons.Edit),
                contentDescription = "Edit pool",
                modifier = Modifier.size(16.dp)
            )
        }

        IconButton(onClick = { onRemovePoolClick(item.id) }) {
            Icon(
                painter = painterResource(id = MNXIcons.Trash),
                contentDescription = "Remove pool",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}