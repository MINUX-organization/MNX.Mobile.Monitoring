package com.minux.monitoring.feature.cryptos.impl.common.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.modifier.shimmerEffect
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel

@Composable
internal fun CoinsDropDownMenu(
    menuItemsIsLoading: Boolean,
    menuItems: List<CryptocurrencyItemModel>?,
    selectedMenuItem: CryptocurrencyItemModel?,
    onSelectedMenuItemChange: (CryptocurrencyItemModel?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Coin",
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            style = LocalTextStyle.current
        )

        when {
            menuItemsIsLoading -> Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )

            menuItems.isNullOrEmpty() -> MNXTextField(
                value = if (menuItems == null) "N/A" else "No coins found",
                onValueChange = {},
                modifier = modifier,
                readOnly = true,
                supportingText = {
                    Text(
                        text = "Error to load coins",
                        modifier = Modifier.padding(start = 2.dp)
                    )
                },
                isError = menuItems == null
            )

            else -> MNXDropDownMenu(
                menuItems = menuItems,
                selectedMenuItem = selectedMenuItem,
                onSelectedMenuItemChange = onSelectedMenuItemChange
            )
        }
    }
}