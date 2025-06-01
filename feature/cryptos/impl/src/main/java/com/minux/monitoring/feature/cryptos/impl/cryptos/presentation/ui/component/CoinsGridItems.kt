package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel

internal fun LazyGridScope.cryptosGridItems(
    item: CryptocurrencyItemModel,
    itemPadding: PaddingValues,
    onRemoveCryptocurrencyClick: (String) -> Unit
) {
    item {
        Text(
            text = item.shortName ?: "N/A",
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        Text(
            text = item.fullName ?: "N/A",
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        Text(
            text = item.algorithm?.name ?: "N/A",
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        CryptosControlsGridItem(
            item = item,
            onRemoveCryptocurrencyClick = onRemoveCryptocurrencyClick
        )
    }
}

@Composable
private fun CryptosControlsGridItem(
    item: CryptocurrencyItemModel,
    onRemoveCryptocurrencyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        IconButton(onClick = { onRemoveCryptocurrencyClick(item.id) }) {
            Icon(
                painter = painterResource(id = MNXIcons.Trash),
                contentDescription = "Remove pool",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}