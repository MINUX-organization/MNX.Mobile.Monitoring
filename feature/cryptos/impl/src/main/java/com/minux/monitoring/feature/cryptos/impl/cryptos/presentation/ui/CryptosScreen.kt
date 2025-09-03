package com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui

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
import com.minux.monitoring.feature.cryptos.impl.common.presentation.model.CryptocurrencyItemModel
import com.minux.monitoring.feature.cryptos.impl.common.presentation.ui.CryptoAssetGrid
import com.minux.monitoring.feature.cryptos.impl.common.presentation.ui.CryptoAssetGridError
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component.AddCoinBottomSheet
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component.CryptosUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.component.cryptosGridItems
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosAction
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosEvent
import com.minux.monitoring.feature.cryptos.impl.cryptos.presentation.ui.model.CryptosUiState
import kotlinx.coroutines.delay

@Composable
internal fun CryptosRoute(
    viewModel: CryptosViewModel,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.cryptosUiState.collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)
    val isAddCoinBottomSheetShow = rememberSaveable { mutableStateOf(false) }

    CryptosScreen(
        cryptosUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )

    AddCoinBottomSheet(
        showSheet = isAddCoinBottomSheetShow.value,
        onShowSheetChange = { isAddCoinBottomSheetShow.value = it },
        cryptosUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier.safeDrawingPadding()
    )

    when (action) {
        CryptosAction.OpenAddCryptoBottomSheet -> isAddCoinBottomSheetShow.value = true

        CryptosAction.CloseAddCryptoBottomSheet -> {
            isAddCoinBottomSheetShow.value = false
            onShowSnackBar("Add cryptocurrency successful")
        }

        is CryptosAction.ShowAddCryptoFailedSnackBar -> {
            val errorInfo = (action as CryptosAction.ShowAddCryptoFailedSnackBar).message
            onShowSnackBar("Add new cryptocurrency failed. $errorInfo")
        }

        CryptosAction.ShowRemoveCryptoSuccessSnackBar -> {
            onShowSnackBar("Remove cryptocurrency successful")
        }

        is CryptosAction.ShowRemoveCryptoFailedSnackBar -> {
            val errorInfo = (action as CryptosAction.ShowRemoveCryptoFailedSnackBar).message
            onShowSnackBar("Remove cryptocurrency failed. $errorInfo")
        }

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CryptosScreen(
    cryptosUiState: CryptosUiState,
    onEvent: (CryptosEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(CryptosEvent.FetchCryptos)
    }

    Column(modifier = modifier) {
        Text(
            text = "Cryptos",
            style = MNXTypography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        SearchTextField(
            query = cryptosUiState.searchQuery,
            onQueryChange = { onEvent(CryptosEvent.SearchQueryChanged(searchQuery = it)) },
            enabled = !cryptosUiState.cryptosIsLoading && !cryptosUiState.cryptos.isNullOrEmpty(),
            placeholder = { Text(text = "Search") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        val isRefreshing = remember { mutableStateOf(false) }

        LaunchedEffect(isRefreshing.value) {
            if (isRefreshing.value) {
                onEvent(CryptosEvent.FetchCryptos)
                delay(200)
                isRefreshing.value = false
            }
        }

        PullToRefreshBox(
            isRefreshing = isRefreshing.value,
            onRefresh = { isRefreshing.value = true },
            modifier = Modifier.weight(1f)
        ) {
            CryptoAssetGrid<CryptocurrencyItemModel>(
                headers = listOf("Name", "Full Name", "Algorithm", ""),
                itemsIsLoading = cryptosUiState.cryptosIsLoading,
                itemsPlaceholder = {
                    CryptoAssetGridError(
                        text = "Failed to load cryptocurrencies",
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )
                },
                items = cryptosUiState.filteredCryptos,
                modifier = Modifier.fillMaxSize()
            ) { item, itemPadding ->
                cryptosGridItems(
                    item = item,
                    itemPadding = itemPadding,
                    onRemoveCryptocurrencyClick = { onEvent(CryptosEvent.RemoveCryptocurrency(id = it)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        MNXBorderedButton(
            onClick = { onEvent(CryptosEvent.AddCryptocurrency) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Add cryptocurrency",
                style = MNXTypography.titleSmall
            )
        }
    }
}

@Preview
@Composable
private fun CryptosScreenPreview(
    @PreviewParameter(CryptosUiStatePreviewParameterProvider::class)
    cryptosUiState: CryptosUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CryptosScreen(
                cryptosUiState = cryptosUiState,
                onEvent = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}