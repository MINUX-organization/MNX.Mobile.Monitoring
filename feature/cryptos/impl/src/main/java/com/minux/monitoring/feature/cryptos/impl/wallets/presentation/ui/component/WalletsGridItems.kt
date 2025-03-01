package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletItemModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsEvent

internal fun LazyGridScope.walletsGridItems(
    item: WalletItemModel,
    itemPadding: PaddingValues,
    onSelectWallet: (WalletsEvent.SelectWallet) -> Unit,
    onRemoveWallet: (WalletsEvent.RemoveWallet) -> Unit
) {
    item {
        Text(
            text = item.name,
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        Text(
            text = item.cryptocurrency,
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        WalletAddressGridItem(
            walletAddress = item.address,
            modifier = Modifier.padding(paddingValues = itemPadding)
        )
    }

    item {
        WalletControlsGridItem(
            item = item,
            onSelectWallet = onSelectWallet,
            onRemoveWallet = onRemoveWallet,
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}

@Composable
private fun WalletAddressGridItem(
    walletAddress: String,
    modifier: Modifier = Modifier
) {
    val showWalletAddress = remember {
        mutableStateOf(true)
    }

    if (showWalletAddress.value) {
        Text(
            modifier = modifier.clickable { showWalletAddress.value = false },
            text = walletAddress
        )
    } else {
        Box(modifier = modifier) {
            Icon(
                modifier = Modifier
                    .size(width = 23.dp, height = 25.dp)
                    .offset(x = (-4).dp)
                    .clickable { showWalletAddress.value = true },
                painter = painterResource(id = MNXIcons.Show),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Delete item"
            )
        }
    }
}

@Composable
private fun WalletControlsGridItem(
    item: WalletItemModel,
    onSelectWallet: (WalletsEvent.SelectWallet) -> Unit,
    onRemoveWallet: (WalletsEvent.RemoveWallet) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = MNXIcons.Edit),
            contentDescription = "Edit wallet address",
            modifier = Modifier
                .size(18.dp)
                .clickable { onSelectWallet(WalletsEvent.SelectWallet(wallet = item)) },
        )

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Remove wallet",
            modifier = Modifier
                .size(24.dp)
                .clickable { onRemoveWallet(WalletsEvent.RemoveWallet(id = item.id)) },
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}