package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.component.MNXBottomSheet
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsEvent
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChangeWalletBottomSheet(
    showSheet: Boolean,
    onShowSheetChange: (Boolean) -> Unit,
    walletsUiState: WalletsUiState,
    onEvent: (WalletsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    MNXBottomSheet(
        showSheet = showSheet,
        onShowSheetChange = onShowSheetChange,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            val wallet = remember { walletsUiState.selectedWallet!!.walletInput }

            WalletInputFields(
                model = wallet,
                coins = walletsUiState.coins,
                onEvent = onEvent
            )

            MNXBorderedButton(
                onClick = { onEvent(WalletsEvent.ChangeWallet) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Change",
                    style = MNXTypography.titleSmall
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChangeWalletBottomSheetPreview(
    @PreviewParameter(WalletsUiStatePreviewParameterProvider::class)
    walletsUiState: WalletsUiState
) {
    MNXTheme {
        ChangeWalletBottomSheet(
            showSheet = true,
            onShowSheetChange = {},
            walletsUiState = walletsUiState,
            onEvent = {}
        )
    }
}