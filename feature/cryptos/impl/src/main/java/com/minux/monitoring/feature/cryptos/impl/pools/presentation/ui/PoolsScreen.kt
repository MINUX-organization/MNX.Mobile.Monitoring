package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui

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
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.model.PoolItemModel
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component.AddPoolBottomSheet
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component.ChangePoolBottomSheet
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component.PoolsUiStatePreviewParameterProvider
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.component.poolsGridItems
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsAction
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsEvent
import com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model.PoolsUiState
import kotlinx.coroutines.delay

@Composable
internal fun PoolsRoute(
    viewModel: PoolsViewModel,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.poolsUiState.collectAsStateWithLifecycle()
    val action by viewModel.uiActions().collectAsStateWithLifecycle(initialValue = null)
    val isAddPoolBottomSheetShow = rememberSaveable { mutableStateOf(false) }
    val isChangePoolBottomSheetShow = rememberSaveable { mutableStateOf(false) }

    PoolsScreen(
        poolsUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )

    AddPoolBottomSheet(
        showSheet = isAddPoolBottomSheetShow.value,
        onShowSheetChange = { isAddPoolBottomSheetShow.value = it },
        poolsUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier.safeDrawingPadding()
    )

    ChangePoolBottomSheet(
        showSheet = isChangePoolBottomSheetShow.value,
        onShowSheetChange = { isChangePoolBottomSheetShow.value = it },
        poolsUiState = state,
        onEvent = viewModel::onEvent,
        modifier = Modifier.safeDrawingPadding()
    )

    when (action) {
        PoolsAction.OpenAddPoolBottomSheet -> isAddPoolBottomSheetShow.value = true

        PoolsAction.CloseAddPoolBottomSheet -> {
            isAddPoolBottomSheetShow.value = false
            onShowSnackBar("Add pool successful")
        }

        PoolsAction.OpenChangePoolBottomSheet -> isChangePoolBottomSheetShow.value = true

        PoolsAction.CloseChangePoolBottomSheet -> {
            isChangePoolBottomSheetShow.value = false
            onShowSnackBar("Change pool successful")
        }

        is PoolsAction.ShowAddPoolFailedSnackBar -> {
            val errorInfo = (action as PoolsAction.ShowAddPoolFailedSnackBar).message
            onShowSnackBar("Add new pool failed. $errorInfo")
        }

        is PoolsAction.ShowChangePoolFailedSnackBar -> {
            val errorInfo = (action as PoolsAction.ShowChangePoolFailedSnackBar).message
            onShowSnackBar("Change pool failed. $errorInfo")
        }

        PoolsAction.ShowRemovePoolSuccessSnackBar -> {
            onShowSnackBar("Remove pool successful")
        }

        is PoolsAction.ShowRemovePoolFailedSnackBar -> {
            val errorInfo = (action as PoolsAction.ShowRemovePoolFailedSnackBar).message
            onShowSnackBar("Remove pool failed. $errorInfo")
        }

        null -> {}
    }

    if (action != null) viewModel.clearAction()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PoolsScreen(
    poolsUiState: PoolsUiState,
    onEvent: (PoolsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onEvent(PoolsEvent.FetchPools)
    }

    Column(modifier = modifier) {
        Text(
            text = "Pools",
            style = MNXTypography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        SearchTextField(
            query = poolsUiState.searchQuery,
            onQueryChange = { onEvent(PoolsEvent.SearchQueryChanged(searchQuery = it)) },
            enabled = !poolsUiState.poolsIsLoading && !poolsUiState.pools.isNullOrEmpty(),
            placeholder = { Text(text = "Search") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        val isRefreshing = remember { mutableStateOf(false) }

        LaunchedEffect(isRefreshing.value) {
            if (isRefreshing.value) {
                onEvent(PoolsEvent.FetchPools)
                delay(200)
                isRefreshing.value = false
            }
        }

        PullToRefreshBox(
            isRefreshing = isRefreshing.value,
            onRefresh = { isRefreshing.value = true },
            modifier = Modifier.weight(1f)
        ) {
            CryptoAssetGrid<PoolItemModel>(
                headers = listOf("Domain", "Port", "Coin", ""),
                itemsIsLoading = poolsUiState.poolsIsLoading,
                itemsPlaceholder = {
                    CryptoAssetGridError(
                        text = "Failed to load pools",
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )
                },
                items = poolsUiState.filteredPools,
                modifier = Modifier.fillMaxSize()
            ) { item, itemPadding ->
                poolsGridItems(
                    item = item,
                    itemPadding = itemPadding,
                    onChangePoolClick = { onEvent(PoolsEvent.ChangePool(pool = it)) },
                    onRemovePoolClick = { onEvent(PoolsEvent.RemovePool(id = it)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        MNXBorderedButton(
            onClick = { onEvent(PoolsEvent.AddPool) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Add pool",
                style = MNXTypography.titleSmall
            )
        }
    }
}

@Preview
@Composable
private fun PoolsScreenPreview(
    @PreviewParameter(PoolsUiStatePreviewParameterProvider::class)
    poolsUiState: PoolsUiState
) {
    MNXTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PoolsScreen(
                poolsUiState = poolsUiState,
                onEvent = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}