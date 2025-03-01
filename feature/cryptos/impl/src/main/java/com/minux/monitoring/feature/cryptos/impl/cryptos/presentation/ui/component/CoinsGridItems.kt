package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosEvent

internal fun LazyGridScope.cryptosGridItems(
    item: CryptocurrencyItemModel,
    itemPadding: PaddingValues,
    onRemoveCryptocurrency: (CryptosEvent.RemoveCryptocurrency) -> Unit
) {
    item {
        Text(
            text = item.shortName,
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        Text(
            text = item.fullName,
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        Text(
            text = item.algorithm.name,
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        CryptosControlsGridItem(
            item = item,
            onRemoveCryptocurrency = onRemoveCryptocurrency,
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }
}

@Composable
private fun CryptosControlsGridItem(
    item: CryptocurrencyItemModel,
    onRemoveCryptocurrency: (CryptosEvent.RemoveCryptocurrency) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier
                .size(width = 23.dp, height = 25.dp)
                .clickable {
                    onRemoveCryptocurrency(CryptosEvent.RemoveCryptocurrency(id = item.id))
                },
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete item"
        )
    }
}