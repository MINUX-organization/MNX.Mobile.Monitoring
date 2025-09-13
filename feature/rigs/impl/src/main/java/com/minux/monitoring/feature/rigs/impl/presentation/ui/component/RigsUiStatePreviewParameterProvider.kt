package com.minux.monitoring.feature.rigs.impl.presentation.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.minux.monitoring.feature.rigs.impl.presentation.ui.model.RigsUiState

internal class RigsUiStatePreviewParameterProvider : PreviewParameterProvider<RigsUiState> {
    private val rigs = RigItemPreviewParameterProvider().values.toList()

    private val uiState = RigsUiState(
        rigs = rigs,
        filteredRigs = rigs
    )

    override val values: Sequence<RigsUiState> = sequenceOf(
        uiState,
        uiState.copy(rigsIsLoading = false)
    )
}