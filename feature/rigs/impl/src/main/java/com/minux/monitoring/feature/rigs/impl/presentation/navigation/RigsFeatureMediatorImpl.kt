package com.minux.monitoring.feature.rigs.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.rigs.api.RigsFeatureMediator
import com.minux.monitoring.feature.rigs.impl.di.RigsComponentHolder
import com.minux.monitoring.feature.rigs.impl.presentation.ui.RigsRoute
import com.minux.monitoring.feature.rigs.impl.presentation.ui.RigsViewModel
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle

internal class RigsFeatureMediatorImpl : RigsFeatureMediator {

    @Composable
    override fun AddRigsScreen(entry: NavBackStackEntry, onShowSnackBar: (String) -> Unit) {
        BindApiToEntryLifecycle(
            holder = RigsComponentHolder,
            navEntry = entry
        ) {
            val component = remember { RigsComponentHolder.fetchComponent() }
            val rigsViewModel = viewModel<RigsViewModel>(factory = component.viewModelFactory)

            RigsRoute(
                viewModel = rigsViewModel,
                onShowSnackBar = onShowSnackBar
            )
        }
    }
}