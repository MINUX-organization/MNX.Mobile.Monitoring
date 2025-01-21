package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.ui.CoinsDropDownMenu
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletInputModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsEvent

@Composable
internal fun WalletInputFields(
    model: WalletInputModel,
    coins: List<CryptocurrencyItemModel>,
    onEvent: (WalletsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NameField(
            value = model.name,
            onValueChange = { onEvent(WalletsEvent.NameChanged(name = it)) },
            isValid = !model.isValidationShowed || model.isNameValid,
            modifier = Modifier.fillMaxWidth()
        )

        CoinsDropDownMenu(
            menuItems = coins,
            selectedMenuItem = model.cryptocurrency,
            onSelectedMenuItemChange = { onEvent(WalletsEvent.CoinChanged(coin = it)) },
            isValid = model.isCoinValid,
            modifier = Modifier.fillMaxWidth()
        )

        AddressField(
            value = model.address,
            onValueChange = { onEvent(WalletsEvent.AddressChanged(address = it)) },
            isValid = !model.isValidationShowed || model.isAddressValid,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun NameField(
    value: String,
    onValueChange: (String) -> Unit,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    MNXTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
            Text(
                text = "Name",
                modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
            )
        },
        placeholder = { Text(text = "Enter name") },
        supportingText = {
            Text(
                text = "Name is empty",
                modifier = Modifier.padding(start = 2.dp)
            )
        },
        isError = !isValid
    )
}

@Composable
private fun AddressField(
    value: String,
    onValueChange: (String) -> Unit,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    MNXTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
            Text(
                text = "Address",
                modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
            )
        },
        placeholder = { Text(text = "Enter address") },
        supportingText = {
            Text(
                text = "Address is empty",
                modifier = Modifier.padding(start = 2.dp)
            )
        },
        isError = !isValid
    )
}

@Preview
@Composable
private fun WalletInputFieldsPreview(modifier: Modifier = Modifier) {
    MNXTheme {
        WalletInputFields(
            model = WalletInputModel(),
            coins = emptyList(),
            onEvent = {}
        )
    }
}