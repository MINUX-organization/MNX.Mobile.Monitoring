package com.minux.monitoring.app.di

import com.minux.monitoring.injector.BaseApi
import com.minux.monitoring.injector.compose.binder.BinderBaseApiToLifecycle

interface AppApi : BaseApi, BinderBaseApiToLifecycle