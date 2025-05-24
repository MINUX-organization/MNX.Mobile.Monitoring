package com.minux.monitoring.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.feature.profile.api.ProfileFeatureMediator
import com.minux.monitoring.feature.profile.impl.di.ProfileComponentHolder
import javax.inject.Inject

class ProfileFeatureMediatorProxy @Inject constructor() : ProfileFeatureMediator {

    @Composable
    override fun AddProfileScreen(
        entry: NavBackStackEntry,
        onNavigateUp: () -> Unit,
        onShowSnackBar: (String) -> Unit
    ) {
        ProfileComponentHolder.fetchApi()
            .profileFeatureMediator
            .AddProfileScreen(
                entry = entry,
                onNavigateUp = onNavigateUp,
                onShowSnackBar = onShowSnackBar
            )
    }
}