package com.minux.monitoring.feature.cryptos.impl.pools.presentation.ui.model

internal sealed interface PoolsAction {
    data object OpenAddPoolBottomSheet : PoolsAction

    data object CloseAddPoolBottomSheet : PoolsAction

    data object OpenChangePoolBottomSheet : PoolsAction

    data object CloseChangePoolBottomSheet : PoolsAction

    class ShowAddPoolFailedSnackBar(val message: String? = "") : PoolsAction

    class ShowChangePoolFailedSnackBar(val message: String? = "") : PoolsAction

    data object ShowRemovePoolSuccessSnackBar : PoolsAction

    class ShowRemovePoolFailedSnackBar(val message: String? = "") : PoolsAction
}