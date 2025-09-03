package com.minux.monitoring.feature.flightsheets.impl.presentation.ui.configuration.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.modifier.shimmerEffect
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningCoinConfigInputModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningPoolModel
import com.minux.monitoring.feature.flightsheets.impl.presentation.model.mining.MiningWalletModel

@Composable
internal fun MiningCoinConfigInputFields(
    model: MiningCoinConfigInputModel,
    poolsIsLoading: Boolean,
    pools: List<MiningPoolModel>?,
    poolsLabel: String,
    walletsIsLoading: Boolean,
    wallets: List<MiningWalletModel>?,
    walletsLabel: String,
    poolPasswordLabel: String,
    onPoolChange: (MiningPoolModel?) -> Unit,
    onWalletChange: (MiningWalletModel?) -> Unit,
    onPoolPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = poolsLabel,
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )

        PoolsDropDownMenu(
            isLoading = poolsIsLoading,
            pools = pools,
            selectedPool = model.selectedPool,
            onPoolChange = onPoolChange
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = walletsLabel,
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )

        WalletsDropDownMenu(
            isLoading = walletsIsLoading,
            wallets = wallets,
            selectedWallet = model.selectedWallet,
            onWalletChange = onWalletChange
        )

        Spacer(modifier = Modifier.height(8.dp))

        MNXTextField(
            value = model.poolPassword ?: "",
            onValueChange = onPoolPasswordChange,
            label = {
                Text(
                    text = poolPasswordLabel,
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
                )
            },
            placeholder = { Text(text = "Enter pool password") }
        )
    }
}

@Composable
private fun PoolsDropDownMenu(
    isLoading: Boolean,
    pools: List<MiningPoolModel>?,
    selectedPool: MiningPoolModel?,
    onPoolChange: (MiningPoolModel?) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        isLoading -> Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )

        pools.isNullOrEmpty() -> MNXTextField(
            value = if (pools == null) "N/A" else "No pools found",
            onValueChange = {},
            readOnly = true,
            supportingText = {
                Text(
                    text = "Error to load pools",
                    modifier = Modifier.padding(start = 2.dp)
                )
            },
            isError = pools == null
        )

        else -> MNXDropDownMenu(
            menuItems = pools,
            selectedMenuItem = selectedPool,
            onSelectedMenuItemChange = onPoolChange,
            modifier = modifier
        )
    }
}

@Composable
private fun WalletsDropDownMenu(
    isLoading: Boolean,
    wallets: List<MiningWalletModel>?,
    selectedWallet: MiningWalletModel?,
    onWalletChange: (MiningWalletModel?) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        isLoading -> Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )

        wallets.isNullOrEmpty() -> MNXTextField(
            value = if (wallets == null) "N/A" else "No wallets found",
            onValueChange = {},
            readOnly = true,
            supportingText = {
                Text(
                    text = "Error to load wallets",
                    modifier = Modifier.padding(start = 2.dp)
                )
            },
            isError = wallets == null
        )

        else -> MNXDropDownMenu(
            menuItems = wallets,
            selectedMenuItem = selectedWallet,
            onSelectedMenuItemChange = onWalletChange,
            modifier = modifier
        )
    }
}