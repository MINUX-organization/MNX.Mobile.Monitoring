package com.minux.monitoring.ui.main.component

import com.minux.monitoring.ui.main.navigation.MainFlowRoute

internal sealed interface NavigationDrawerItemModel {
    val title: String

    class Single(
        val route: MainFlowRoute,
        override val title: String
    ) : NavigationDrawerItemModel

    class Group(
        val items: List<Single>,
        override val title: String
    ) : NavigationDrawerItemModel
}