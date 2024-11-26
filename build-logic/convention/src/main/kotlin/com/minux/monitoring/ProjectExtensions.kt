package com.minux.monitoring

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.kotlin.dsl.getByType
import java.util.Optional

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Optional<VersionConstraint>.getVersion(): Int {
    return get()
        .toString()
        .toInt()
}

internal fun Optional<VersionConstraint>.getStrVersion(): String {
    return get()
        .toString()
}