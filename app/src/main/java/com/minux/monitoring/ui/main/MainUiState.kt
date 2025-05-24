package com.minux.monitoring.ui.main

import com.minux.monitoring.ui.main.model.ProfileOverviewModel

internal data class MainUiState(
    val profileOverview: ProfileOverviewModel = ProfileOverviewModel()
)