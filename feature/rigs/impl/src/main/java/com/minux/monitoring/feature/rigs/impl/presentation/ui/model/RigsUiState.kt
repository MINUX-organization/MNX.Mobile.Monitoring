package com.minux.monitoring.feature.rigs.impl.presentation.ui.model

import com.minux.monitoring.feature.rigs.impl.presentation.model.RigItemModel

internal data class RigsUiState(
    val rigsIsLoading: Boolean = true,
    val rigs: List<RigItemModel>? = null,
    val searchQuery: String = "",
    val filteredRigs: List<RigItemModel>? = null
)