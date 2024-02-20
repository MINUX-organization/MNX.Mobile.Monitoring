package com.minux.monitoring.core.network.api

import kotlin.reflect.KClass

/** Receive data from the Hub */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Receive(
    /**
     * Hub method name.
     */
    val value: String = "",
    vararg val paramTypes: KClass<*>
)