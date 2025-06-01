package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXBottomSheet
import com.minux.monitoring.core.designsystem.component.MNXButton
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosEvent
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddCoinBottomSheet(
    showSheet: Boolean,
    onShowSheetChange: (Boolean) -> Unit,
    cryptosUiState: CryptosUiState,
    onEvent: (CryptosEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    MNXBottomSheet(
        showSheet = showSheet,
        onShowSheetChange = onShowSheetChange,
        modifier = modifier,
        sheetState = sheetState
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
            Text(
                text = "Add cryptocurrency",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MNXTypography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            CoinInputFields(
                model = cryptosUiState.cryptocurrencyInput,
                algorithmsIsLoading = cryptosUiState.cryptoAlgorithmsIsLoading,
                algorithms = cryptosUiState.cryptoAlgorithms,
                onEvent = onEvent
            )

            Spacer(modifier = Modifier.height(8.dp))

            MNXButton(
                onClick = { onEvent(CryptosEvent.ConfirmAddCryptocurrency) },
                modifier = Modifier.fillMaxWidth(),
                enabled = cryptosUiState.cryptocurrencyInput.run {
                    isShortNameValid && isFullNameValid && isAlgorithmValid
                }
            ) {
                Text(
                    text = "Confirm",
                    style = MNXTypography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}