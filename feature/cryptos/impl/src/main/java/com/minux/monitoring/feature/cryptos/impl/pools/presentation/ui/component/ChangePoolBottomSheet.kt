package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.minux.monitoring.core.designsystem.component.MNXBottomSheet
import com.minux.monitoring.core.designsystem.component.MNXButton
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsEvent
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChangePoolBottomSheet(
    showSheet: Boolean,
    onShowSheetChange: (Boolean) -> Unit,
    poolsUiState: PoolsUiState,
    onEvent: (PoolsEvent) -> Unit,
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
                text = "Edit pool",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MNXTypography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            PoolInputFields(
                model = poolsUiState.poolInput,
                coinsIsLoading = poolsUiState.coinsIsLoading,
                coins = poolsUiState.coins,
                onEvent = onEvent
            )

            Spacer(modifier = Modifier.height(8.dp))

            MNXButton(
                onClick = { onEvent(PoolsEvent.ConfirmChangePool) },
                modifier = Modifier.fillMaxWidth(),
                enabled = poolsUiState.poolInput.run { isDomainAddressValid && isPortValid && isCoinValid }
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

@Preview
@Composable
private fun ChangePoolBottomSheetPreview(
    @PreviewParameter(PoolsUiStatePreviewParameterProvider::class)
    poolsUiState: PoolsUiState
) {
    MNXTheme {
        ChangePoolBottomSheet(
            showSheet = true,
            onShowSheetChange = {},
            poolsUiState = poolsUiState,
            onEvent = {}
        )
    }
}