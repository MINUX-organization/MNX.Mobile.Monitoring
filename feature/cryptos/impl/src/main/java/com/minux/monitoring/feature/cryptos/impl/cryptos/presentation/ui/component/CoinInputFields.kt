package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXDropDownMenu
import com.minux.monitoring.core.designsystem.component.MNXTextField
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.AlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyInputModel
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosEvent

@Composable
internal fun CoinInputFields(
    model: CryptocurrencyInputModel,
    algorithms: List<AlgorithmItemModel>,
    onEvent: (CryptosEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ShortNameField(
            value = model.shortName,
            onValueChange = { onEvent(CryptosEvent.ShortNameChanged(shortName = it)) },
            isValid = !model.isValidationShowed || model.isShortNameValid,
            modifier = Modifier.fillMaxWidth()
        )

        FullNameField(
            value = model.fullName,
            onValueChange = { onEvent(CryptosEvent.FullNameChanged(fullName = it)) },
            isValid = !model.isValidationShowed || model.isFullNameValid,
            modifier = Modifier.fillMaxWidth()
        )

        AlgorithmsDropDownMenu(
            menuItems = algorithms,
            selectedMenuItem = model.algorithm,
            onSelectedMenuItemChange = { onEvent(CryptosEvent.AlgorithmChanged(algorithm = it)) },
            isValid = model.isAlgorithmValid,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ShortNameField(
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
        placeholder = { Text(text = "Enter short name") },
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
private fun FullNameField(
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
                text = "Full name",
                modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
            )
        },
        placeholder = { Text(text = "Enter full name") },
        supportingText = {
            Text(
                text = "Full name is empty",
                modifier = Modifier.padding(start = 2.dp)
            )
        },
        isError = !isValid
    )
}

@Composable
private fun AlgorithmsDropDownMenu(
    menuItems: List<AlgorithmItemModel>,
    selectedMenuItem: AlgorithmItemModel?,
    onSelectedMenuItemChange: (AlgorithmItemModel) -> Unit,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    val algorithmsLabel = @Composable {
        Text(
            text = "Algorithm",
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
        )
    }

    if (isValid) {
        MNXDropDownMenu(
            menuItems = menuItems,
            selectedMenuItem = selectedMenuItem ?: menuItems.first(),
            onSelectedMenuItemChange = onSelectedMenuItemChange,
            modifier = modifier,
            label = algorithmsLabel
        )
    } else {
        MNXTextField(
            value = "N/A",
            onValueChange = {},
            modifier = modifier,
            readOnly = true,
            label = algorithmsLabel,
            supportingText = {
                Text(
                    text = "Error to load algorithms",
                    modifier = Modifier.padding(start = 2.dp)
                )
            },
            isError = true
        )
    }
}