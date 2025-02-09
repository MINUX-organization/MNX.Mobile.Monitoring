package com.minux.monitoring.injector.compose.binder

import androidx.lifecycle.LifecycleEventObserver

interface BinderBaseApi {
    fun bind(entryId: String): LifecycleEventObserver
}