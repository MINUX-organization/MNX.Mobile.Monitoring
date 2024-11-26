package com.minux.monitoring.injector

interface BaseDependencies {
    val dependencyHolder: BaseDependencyHolder<out BaseDependencies>
}

interface BaseDependencyHolder<Dependencies : BaseDependencies> {
    val dependencies: Dependencies
}

abstract class DependencyHolder<Dependencies : BaseDependencies> : BaseDependencyHolder<Dependencies> {
    abstract val block: (BaseDependencyHolder<Dependencies>) -> Dependencies

    override val dependencies: Dependencies
        get() = block(this)
}

abstract class DependencyHolderWithOneApi<Dependencies : BaseDependencies, FirstApi : BaseApi>(
    private val firstApi: FirstApi
) : BaseDependencyHolder<Dependencies> {
    abstract val block: (BaseDependencyHolder<Dependencies>, FirstApi) -> Dependencies

    override val dependencies: Dependencies
        get() = block(this, firstApi)
}

abstract class DependencyHolderWithTwoApi<Dependencies : BaseDependencies, FirstApi : BaseApi, SecondApi : BaseApi>(
    private val firstApi : FirstApi,
    private val secondApi : SecondApi
) : BaseDependencyHolder<Dependencies> {
    abstract val block: (BaseDependencyHolder<Dependencies>, FirstApi, SecondApi) -> Dependencies

    override val dependencies: Dependencies
        get() = block(this, firstApi, secondApi)
}

abstract class DependencyHolderWithThreeApi<Dependencies : BaseDependencies, FirstApi : BaseApi, SecondApi : BaseApi, ThreeApi : BaseApi>(
    private val firstApi : FirstApi,
    private val secondApi : SecondApi,
    private val threeApi : ThreeApi
) : BaseDependencyHolder<Dependencies> {
    abstract val block: (BaseDependencyHolder<Dependencies>, FirstApi, SecondApi, ThreeApi) -> Dependencies

    override val dependencies: Dependencies
        get() = block(this, firstApi, secondApi, threeApi)
}