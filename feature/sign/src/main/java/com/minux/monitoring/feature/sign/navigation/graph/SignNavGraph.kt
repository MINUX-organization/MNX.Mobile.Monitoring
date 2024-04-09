package com.minux.monitoring.feature.sign.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.minux.monitoring.feature.sign.navigation.SignDestinations
import com.minux.monitoring.feature.sign.navigation.SignGraphs

fun NavGraphBuilder.signNavGraph(
    navigateToMain: () -> Unit
) {
    navigation(
        route = SignGraphs.SignNavGraph.name,
        startDestination = SignDestinations.SignInTypeRoute.name
    ) {

    }
}