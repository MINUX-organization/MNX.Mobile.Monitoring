package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.ui.CoinsDropDownMenu
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolInputModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsEvent

@Composable
internal fun PoolInputFields(
    model: PoolInputModel,
    coins: List<CryptocurrencyItemModel>,
    onEvent: (PoolsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DomainAddressField(
            value = model.domain,
            onValueChange = { onEvent(PoolsEvent.DomainAddressChanged(domain = it)) },
            isValid = !model.isValidationShowed || model.isDomainAddressValid,
            modifier = Modifier.fillMaxWidth()
        )

        PortField(
            value = model.port,
            onValueChange = { onEvent(PoolsEvent.PortChanged(port = it)) },
            isValid = !model.isValidationShowed || model.isPortValid,
            modifier = Modifier.fillMaxWidth()
        )

        CoinsDropDownMenu(
            menuItems = coins,
            selectedMenuItem = model.cryptocurrency,
            onSelectedMenuItemChange = { onEvent(PoolsEvent.CoinChanged(coin = it)) },
            isValid = model.isCoinValid,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun DomainAddressField(
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
                text = "Domain",
                modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
            )
        },
        placeholder = { Text(text = "Enter domain address") },
        supportingText = {
            Text(
                text = "Invalid domain format",
                modifier = Modifier.padding(start = 2.dp)
            )
        },
        isError = !isValid
    )
}

@Composable
private fun PortField(
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
                text = "Port (0-65535)",
                modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
            )
        },
        placeholder = { Text(text = "Enter port") },
        supportingText = {
            Text(
                text = "Invalid port number",
                modifier = Modifier.padding(start = 2.dp)
            )
        },
        isError = !isValid,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Preview
@Composable
private fun PoolInputFieldsPreview(modifier: Modifier = Modifier) {
    MNXTheme {
        PoolInputFields(
            model = PoolInputModel(),
            coins = emptyList(),
            onEvent = {}
        )
    }
}