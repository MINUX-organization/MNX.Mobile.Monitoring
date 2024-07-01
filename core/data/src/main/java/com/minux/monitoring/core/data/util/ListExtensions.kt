package com.minux.monitoring.core.data.util

fun <T> List<T>.copy(index: Int, element: T): List<T> {
    return toMutableList().apply {
        this[index] = element
    }
}