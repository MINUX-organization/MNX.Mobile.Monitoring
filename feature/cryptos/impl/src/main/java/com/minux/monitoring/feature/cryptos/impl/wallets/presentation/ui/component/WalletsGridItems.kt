package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component

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
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletItemModel

internal fun LazyGridScope.walletsGridItems(
    item: WalletItemModel,
    itemPadding: PaddingValues,
    onChangeWalletClick: (WalletItemModel) -> Unit,
    onRemoveWalletClick: (String) -> Unit
) {
    item {
        Text(
            text = item.name ?: "N/A",
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
        Text(
            text = item.address ?: "N/A",
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        WalletControlsGridItem(
            item = item,
            onChangeWalletClick = onChangeWalletClick,
            onRemoveWalletClick = onRemoveWalletClick
        )
    }
}

@Composable
private fun WalletControlsGridItem(
    item: WalletItemModel,
    onChangeWalletClick: (WalletItemModel) -> Unit,
    onRemoveWalletClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onChangeWalletClick(item) }) {
            Icon(
                painter = painterResource(id = MNXIcons.Edit),
                contentDescription = "Edit wallet",
                modifier = Modifier.size(16.dp)
            )
        }

        IconButton(onClick = { onRemoveWalletClick(item.id) }) {
            Icon(
                painter = painterResource(id = MNXIcons.Trash),
                contentDescription = "Remove wallet",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}