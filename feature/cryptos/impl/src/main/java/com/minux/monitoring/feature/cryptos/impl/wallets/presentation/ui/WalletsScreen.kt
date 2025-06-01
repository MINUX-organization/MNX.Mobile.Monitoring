package com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.core.designsystem.component.MNXBorderedButton
import com.minux.monitoring.core.designsystem.theme.MNXTheme
import com.minux.monitoring.core.designsystem.theme.MNXTypography
import com.minux.monitoring.core.ui.SearchTextField
import com.minux.monitoring.feature.cryptos.impl.common.presentation.ui.CryptoAssetGrid
import com.minux.monitoring.feature.cryptos.impl.common.presentation.ui.CryptoAssetGridError
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.model.WalletItemModel
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component.AddWalletBottomSheet
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component.ChangeWalletBottomSheet
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component.WalletsUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.component.walletsGridItems
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsAction
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsEvent
import com.minux.monitoring.feature.cryptos.impl.wallets.presentation.ui.model.WalletsUiState
import kotlinx.coroutines.delay

@Composable
internal fun WalletsRoute(
    viewModel: WalletsViewModel,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.walletsState.collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)
    val isAddWalletBottomSheetShow = rememberSaveable { mutableStateOf(false) }
    val isChangeWalletBottomSheetShow = rememberSaveable { mutableStateOf(false) }

    WalletsScreen(
        walletsUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )

    AddWalletBottomSheet(
        showSheet = isAddWalletBottomSheetShow.value,
        onShowSheetChange = { isAddWalletBottomSheetShow.value = it },
        walletsUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier.safeDrawingPadding()
    )

    ChangeWalletBottomSheet(
        showSheet = isChangeWalletBottomSheetShow.value,
        onShowSheetChange = { isChangeWalletBottomSheetShow.value = it },
        walletsUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier.safeDrawingPadding()
    )

    when (action) {
        WalletsAction.OpenAddWalletBottomSheet -> isAddWalletBottomSheetShow.value = true

        WalletsAction.CloseAddWalletBottomSheet -> {
            isAddWalletBottomSheetShow.value = false
            onShowSnackBar("Add wallet successful")
        }

        WalletsAction.OpenChangeWalletBottomSheet -> isChangeWalletBottomSheetShow.value = true

        WalletsAction.CloseChangeWalletBottomSheet -> {
            isChangeWalletBottomSheetShow.value = false
            onShowSnackBar("Change wallet successful")
        }

        is WalletsAction.ShowAddWalletFailedSnackBar -> {
            val errorInfo = (action as WalletsAction.ShowAddWalletFailedSnackBar).message
            onShowSnackBar("Add new wallet failed. $errorInfo")
        }

        is WalletsAction.ShowChangeWalletFailedSnackBar -> {
            val errorInfo = (action as WalletsAction.ShowChangeWalletFailedSnackBar).message
            onShowSnackBar("Change wallet failed. $errorInfo")
        }

        WalletsAction.ShowRemoveWalletSuccessSnackBar -> {
            onShowSnackBar("Remove wallet successful")
        }

        is WalletsAction.ShowRemoveWalletFailedSnackBar -> {
            val errorInfo = (action as WalletsAction.ShowRemoveWalletFailedSnackBar).message
            onShowSnackBar("Remove wallet failed. $errorInfo")
        }

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WalletsScreen(
    walletsUiState: WalletsUiState,
    onEvent: (WalletsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(WalletsEvent.FetchWallets)
    }

    Column(modifier = modifier) {
        Text(
            text = "Wallets",
            style = MNXTypography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        SearchTextField(
            query = walletsUiState.searchQuery,
            onQueryChange = { onEvent(WalletsEvent.SearchQueryChanged(searchQuery = it)) },
            enabled = !walletsUiState.walletsIsLoading && !walletsUiState.wallets.isNullOrEmpty(),
            placeholder = { Text(text = "Search") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        val isRefreshing = remember { mutableStateOf(false) }

        LaunchedEffect(isRefreshing.value) {
            if (isRefreshing.value) {
                onEvent(WalletsEvent.FetchWallets)
                delay(200)
                isRefreshing.value = false
            }
        }

        PullToRefreshBox(
            isRefreshing = isRefreshing.value,
            onRefresh = { isRefreshing.value = true },
            modifier = Modifier.weight(1f)
        ) {
            CryptoAssetGrid<WalletItemModel>(
                headers = listOf("Name", "Coin", "Address", ""),
                itemsIsLoading = walletsUiState.walletsIsLoading,
                itemsPlaceholder = {
                    CryptoAssetGridError(
                        text = "Failed to load wallets",
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )
                },
                items = walletsUiState.filteredWallets,
                modifier = Modifier.fillMaxSize()
            ) { item, itemPadding ->
                walletsGridItems(
                    item = item,
                    itemPadding = itemPadding,
                    onChangeWalletClick = { onEvent(WalletsEvent.ChangeWallet(wallet = it)) },
                    onRemoveWalletClick = { onEvent(WalletsEvent.RemoveWallet(id = it)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        MNXBorderedButton(
            onClick = { onEvent(WalletsEvent.AddWallet) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Add wallet",
                style = MNXTypography.titleSmall
            )
        }
    }
}

@Preview
@Composable
private fun WalletsScreenPreview(
    @PreviewParameter(WalletsUiStatePreviewParameterProvider::class)
    walletsUiState: WalletsUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            WalletsScreen(
                walletsUiState = walletsUiState,
                onEvent = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}