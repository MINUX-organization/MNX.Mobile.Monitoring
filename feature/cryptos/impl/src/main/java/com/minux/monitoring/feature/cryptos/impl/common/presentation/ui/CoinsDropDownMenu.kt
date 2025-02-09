package com.minux.monitoring.feature.cryptos.impl.common.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel

@Composable
internal fun CoinsDropDownMenu(
    menuItems: List<CryptocurrencyItemModel>,
    selectedMenuItem: CryptocurrencyItemModel?,
    onSelectedMenuItemChange: (CryptocurrencyItemModel) -> Unit,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    val coinsLabel = @Composable {
        Text(
            text = "Coin",
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
        )
    }

    if (isValid) {
        MNXDropDownMenu(
            menuItems = menuItems,
            selectedMenuItem = selectedMenuItem ?: menuItems.first(),
            onSelectedMenuItemChange = onSelectedMenuItemChange,
            modifier = modifier,
            label = coinsLabel
        )
    } else {
        MNXTextField(
            value = "N/A",
            onValueChange = {},
            modifier = modifier,
            readOnly = true,
            label = coinsLabel,
            supportingText = {
                Text(
                    text = "Error to load coins",
                    modifier = Modifier.padding(start = 2.dp)
                )
            },
            isError = true
        )
    }
}