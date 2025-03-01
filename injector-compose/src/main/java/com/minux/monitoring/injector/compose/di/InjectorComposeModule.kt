package com.minux.monitoring.injector.compose.di

import android.app.Application.ActivityLifecycleCallbacks
import com.minux.monitoring.injector.compose.binder.BinderBaseApi
import com.minux.monitoring.injector.compose.binder.BinderBaseApiImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface InjectorComposeModule {

    @Binds
    @Singleton
    fun bindBinderApiToEntryLifecycle(
        binderApiToEntryLifecycleImpl: BinderBaseApiImpl
    ): BinderBaseApi

    @Binds
    @Singleton
    fun bindActivityLifecycleCallbacks(
        binderApiToEntryLifecycleImpl: BinderBaseApiImpl
    ): ActivityLifecycleCallbacks
}