package com.minux.monitoring.injector.compose.binder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import com.minux.monitoring.injector.BaseApi
import com.minux.monitoring.injector.BaseDependencies
import com.minux.monitoring.injector.ComponentHolder

@Composable
inline fun <reified Holder : ComponentHolder<out BaseApi, out BaseDependencies>> BindApiToEntryLifecycle(
    holder: Holder,
    entry: NavBackStackEntry,
    content: @Composable () -> Unit
) {
    val api = remember { holder.fetchApi() }

    checkNotNull(api as? BinderBaseApiToLifecycle) {
        ClassCastException("$api isn't implemented in BinderBaseApiToLifecycle")
    }.run {
        val lifecycleEventObserver = remember {
            binderBaseApi.bind(entryId = entry.id)
        }

        entry.lifecycle.addObserver(lifecycleEventObserver)

        content()
    }
}