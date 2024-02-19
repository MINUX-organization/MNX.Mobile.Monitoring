package com.minux.monitoring.core.network.api

/** Receive data from the Hub */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Receive(
    /**
     * Hub method name.
     */
    val value: String = ""
)