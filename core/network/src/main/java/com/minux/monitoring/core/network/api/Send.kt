package com.minux.monitoring.core.network.api

/** Send data to the Hub */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Send(
    /**
     * Hub method name.
     */
    val value: String = ""
)