package com.minux.monitoring.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.rigs.api.RigsFeatureMediator
import com.minux.monitoring.feature.rigs.impl.di.RigsComponentHolder
import javax.inject.Inject

class RigsFeatureMediatorProxy @Inject constructor() : RigsFeatureMediator {

    @Composable
    override fun AddRigsScreen(
        entry: NavBackStackEntry,
        onShowSnackBar: (String) -> Unit
    ) {
        RigsComponentHolder.fetchApi()
            .rigsFeatureMediator
            .AddRigsScreen(
                entry = entry,
                onShowSnackBar = onShowSnackBar
            )
    }
}