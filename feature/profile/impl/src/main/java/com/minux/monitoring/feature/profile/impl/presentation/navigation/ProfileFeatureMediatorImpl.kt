package com.minux.monitoring.feature.profile.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.profile.api.ProfileFeatureMediator
import com.minux.monitoring.feature.profile.impl.di.ProfileComponentHolder
import com.minux.monitoring.feature.profile.impl.presentation.ui.ProfileRoute
import com.minux.monitoring.feature.profile.impl.presentation.ui.ProfileViewModel
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle

internal class ProfileFeatureMediatorImpl : ProfileFeatureMediator {

    @Composable
    override fun AddProfileScreen(
        entry: NavBackStackEntry,
        onNavigateUp: () -> Unit,
        onShowSnackBar: (String) -> Unit
    ) {
        BindApiToEntryLifecycle(
            holder = ProfileComponentHolder,
            navEntry = entry
        ) {
            val component = remember { ProfileComponentHolder.fetchComponent() }
            val profileViewModel = viewModel<ProfileViewModel>(factory = component.viewModelFactory)

            ProfileRoute(
                viewModel = profileViewModel,
                onNavigateUp = onNavigateUp,
                onShowSnackBar = onShowSnackBar
            )
        }
    }
}