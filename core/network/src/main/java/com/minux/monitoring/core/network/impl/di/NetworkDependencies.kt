package com.minux.monitoring.core.network.impl.di

import android.app.Application
import com.minux.monitoring.injector.BaseDependencies

interface NetworkDependencies : BaseDependencies {
    val context: Application
}