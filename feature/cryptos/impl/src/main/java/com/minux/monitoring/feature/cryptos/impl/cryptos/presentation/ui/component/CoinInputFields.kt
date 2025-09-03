package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
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
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.AlgorithmItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyInputModel
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosEvent

@Composable
internal fun CoinInputFields(
    model: CryptocurrencyInputModel,
    algorithmsIsLoading: Boolean,
    algorithms: List<AlgorithmItemModel>?,
    onEvent: (CryptosEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ShortNameField(
            value = model.shortName ?: "",
            onValueChange = { onEvent(CryptosEvent.ShortNameChanged(shortName = it)) },
            isValid = !model.isShortNameValidationShowed || model.isShortNameValid,
            modifier = Modifier.fillMaxWidth()
        )

        FullNameField(
            value = model.fullName ?: "",
            onValueChange = { onEvent(CryptosEvent.FullNameChanged(fullName = it)) },
            isValid = !model.isFullNameValidationShowed || model.isFullNameValid,
            modifier = Modifier.fillMaxWidth()
        )

        AlgorithmsDropDownMenu(
            menuItemsIsLoading = algorithmsIsLoading,
            menuItems = algorithms,
            selectedMenuItem = model.selectedAlgorithm,
            onSelectedMenuItemChange = { onEvent(CryptosEvent.AlgorithmChanged(algorithm = it)) },
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
    menuItemsIsLoading: Boolean,
    menuItems: List<AlgorithmItemModel>?,
    selectedMenuItem: AlgorithmItemModel?,
    onSelectedMenuItemChange: (AlgorithmItemModel?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Algorithms",
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
                value = if (menuItems == null) "N/A" else "No algorithms found",
                onValueChange = {},
                modifier = modifier,
                readOnly = true,
                supportingText = {
                    Text(
                        text = "Error to load algorithms",
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