package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component

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
    MNXBottomSheet(
        showSheet = showSheet,
        onShowSheetChange = onShowSheetChange,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            val pool = remember { poolsUiState.selectedPool!!.poolInput }

            PoolInputFields(
                model = pool,
                coins = poolsUiState.coins,
                onEvent = onEvent
            )

            MNXBorderedButton(
                onClick = { onEvent(PoolsEvent.ChangePool) },
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