package com.minux.monitoring.injector

import java.lang.ref.WeakReference

interface ComponentHolder<Api : BaseApi, Dependencies : BaseDependencies> {
    val dependencyProvider: (() -> Dependencies)?

    fun fetchApi(): Api
}

class ComponentHolderDelegate<Api : BaseApi, Dependencies : BaseDependencies, Component : Api>(
    private val componentFactory: (Dependencies) -> Component
) : ComponentHolder<Api, Dependencies> {

    private var componentWeakRef: WeakReference<Component>? = null

    override var dependencyProvider: (() -> Dependencies)? = null

    override fun fetchApi(): Api {
        return fetchComponent()
    }

    fun fetchComponent(): Component {
        var component: Component? = null

        synchronized(this) {
            dependencyProvider?.let { provider ->
                component = componentWeakRef?.get()

                if (component == null) {
                    component = componentFactory(provider())
                    componentWeakRef = WeakReference(component)
                }
            } ?: throw IllegalStateException(
                "Dependency provider for component with factory $componentFactory isn't initialized."
            )
        }

        return checkNotNull(component) {
            "Component holder with component factory $componentFactory isn't initialized."
        }
    }
}