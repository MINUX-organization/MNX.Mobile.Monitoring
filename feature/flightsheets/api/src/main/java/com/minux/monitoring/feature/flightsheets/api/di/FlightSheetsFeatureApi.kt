package com.minux.monitoring.feature.flightsheets.api.di

import com.minux.monitoring.feature.flightsheets.api.FlightSheetsFeatureMediator
import com.minux.monitoring.injector.BaseApi
import com.minux.monitoring.injector.compose.binder.BinderBaseApiToLifecycle

interface FlightSheetsFeatureApi : BaseApi, BinderBaseApiToLifecycle {
    val flightSheetsFeatureMediator: FlightSheetsFeatureMediator
}