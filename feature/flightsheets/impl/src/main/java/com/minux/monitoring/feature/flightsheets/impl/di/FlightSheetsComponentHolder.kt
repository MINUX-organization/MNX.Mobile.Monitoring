package com.minux.monitoring.feature.flightsheets.impl.di

import com.minux.monitoring.feature.flightsheets.api.di.FlightSheetsFeatureApi
import com.minux.monitoring.injector.ComponentHolder
import com.minux.monitoring.injector.ComponentHolderDelegate

object FlightSheetsComponentHolder : ComponentHolder<FlightSheetsFeatureApi, FlightSheetsDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            FlightSheetsFeatureApi,
            FlightSheetsDependencies,
            FlightSheetsComponent> { flightSheetsDependencies ->
                FlightSheetsComponent.get(dependencies = flightSheetsDependencies)
            }

    override var dependencyProvider: (() -> FlightSheetsDependencies)?
            by componentHolderDelegate::dependencyProvider

    override fun fetchApi(): FlightSheetsFeatureApi = componentHolderDelegate.fetchApi()

    internal fun fetchComponent(): FlightSheetsComponent = componentHolderDelegate.fetchComponent()
}