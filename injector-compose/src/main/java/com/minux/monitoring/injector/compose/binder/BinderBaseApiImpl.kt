package com.minux.monitoring.injector.compose.binder

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject

class BinderBaseApiImpl @Inject constructor() : BinderBaseApi, ActivityLifecycleCallbacks {
    private val entryLifecycleEventObservers = mutableMapOf<String, EntryLifecycleEventObserver>()
    private var isCanBeRemoved = true

    private class EntryLifecycleEventObserver(
        private val entryId: String,
        private val removedObserver: (entryId: String) -> Unit
    ) : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                removedObserver(entryId)
            }
        }
    }

    override fun bind(entryId: String): LifecycleEventObserver {
        return entryLifecycleEventObservers.getOrPut(entryId) {
            EntryLifecycleEventObserver(
                entryId = entryId,
                removedObserver = ::removeEntryLifecycleEventObserver
            )
        }
    }

    private fun removeEntryLifecycleEventObserver(entryId: String) {
        if (isCanBeRemoved) {
            entryLifecycleEventObservers.remove(entryId)
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityPreDestroyed(activity: Activity) {
        if (activity.isChangingConfigurations) {
            isCanBeRemoved = false
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        isCanBeRemoved = true
    }
}