package com.minux.monitoring.feature.wallets.ui

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minux.monitoring.core.designsystem.icon.MNXIcons
import com.minux.monitoring.core.designsystem.theme.grillSansMtFamily
import com.minux.monitoring.core.domain.model.wallet.Wallet
import com.minux.monitoring.core.domain.model.wallet.WalletChangeParam
import com.minux.monitoring.core.domain.model.wallet.WalletInputParam
import com.minux.monitoring.core.domain.model.wallet.WalletRemoveParam
import com.minux.monitoring.feature.wallets.WalletsEvent

internal fun LazyGridScope.walletsGridItems(
    item: Wallet,
    itemPadding: PaddingValues,
    onChangeWallet: (WalletsEvent.ChangeWallet) -> Unit,
    onRemoveWallet: (WalletsEvent.RemoveWallet) -> Unit
) {
    val textStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = grillSansMtFamily,
        fontWeight = FontWeight.Normal
    )

    item {
        Text(
            modifier = Modifier.padding(paddingValues = itemPadding),
            text = item.name,
            style = textStyle
        )
    }

    item {
        Text(
            modifier = Modifier.padding(paddingValues = itemPadding),
            text = item.cryptocurrency,
            style = textStyle
        )
    }

    item {
        WalletAddressGridItem(
            walletAddress = item.address,
            itemPadding = itemPadding,
            textStyle = textStyle
        )
    }

    item {
        WalletControlsGridItem(
            item = item,
            onChangeWallet = onChangeWallet,
            onRemoveWallet = onRemoveWallet
        )
    }
}

@Composable
private fun WalletAddressGridItem(
    walletAddress: String,
    itemPadding: PaddingValues,
    textStyle: TextStyle
) {
    val showWalletAddress = remember {
        mutableStateOf(true)
    }

    if (showWalletAddress.value) {
        Text(
            modifier = Modifier
                .padding(paddingValues = itemPadding)
                .clickable { showWalletAddress.value = false },
            text = walletAddress,
            style = textStyle
        )
    } else {
        Box(
            modifier = Modifier
                .padding(
                    paddingValues = PaddingValues(vertical = 2.dp)
                )
        ) {
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
    item: Wallet,
    onChangeWallet: (WalletsEvent.ChangeWallet) -> Unit,
    onRemoveWallet: (WalletsEvent.RemoveWallet) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(
                paddingValues = PaddingValues(vertical = 2.dp)
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(width = 20.dp, height = 20.dp)
                .clickable {
                    onChangeWallet(
                        WalletsEvent.ChangeWallet(
                            WalletChangeParam(
                                walletId = item.id,
                                walletData = WalletInputParam(
                                    name = item.name,
                                    address = item.address,
                                    cryptocurrencyId = item.cryptocurrency
                                )
                            )
                        )
                    )
                },
            painter = painterResource(id = MNXIcons.Edit),
            contentDescription = "Edit wallet address"
        )

        Icon(
            modifier = Modifier
                .size(width = 25.dp, height = 25.dp)
                .clickable {
                    onRemoveWallet(
                        WalletsEvent.RemoveWallet(
                            WalletRemoveParam(walletId = item.id)
                        )
                    )
                },
            imageVector = Icons.Default.Delete,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            contentDescription = "Remove wallet"
        )
    }
}