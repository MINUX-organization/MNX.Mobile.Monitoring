package com.minux.monitoring.app.di

import android.app.Application
import com.minux.monitoring.core.network.api.HttpClient
import com.minux.monitoring.core.network.api.di.NetworkApi
import com.minux.monitoring.core.network.api.session.SessionManager
import com.minux.monitoring.core.network.impl.di.NetworkComponentHolder
import com.minux.monitoring.core.network.impl.di.NetworkDependencies
import com.minux.monitoring.feature.auth.impl.di.AuthComponentHolder
import com.minux.monitoring.feature.auth.impl.di.AuthDependencies
import com.minux.monitoring.feature.cryptos.impl.di.CryptosComponentHolder
import com.minux.monitoring.feature.cryptos.impl.di.CryptosDependencies
import com.minux.monitoring.feature.presets.impl.di.PresetsComponentHolder
import com.minux.monitoring.feature.presets.impl.di.PresetsDependencies
import com.minux.monitoring.injector.BaseDependencies
import com.minux.monitoring.injector.BaseDependencyHolder
import com.minux.monitoring.injector.DependencyHolder
import com.minux.monitoring.injector.DependencyHolderWithThreeApi
import com.minux.monitoring.injector.DependencyHolderWithTwoApi
import com.minux.monitoring.injector.compose.api.InjectorComposeApi
import com.minux.monitoring.injector.compose.binder.BinderBaseApi
import com.minux.monitoring.injector.compose.di.InjectorComposeComponentHolder
import com.minux.monitoring.injector.compose.di.InjectorComposeDependencies
import com.minux.monitoring.navigation.di.NavigationApi
import com.minux.monitoring.navigation.di.NavigationComponentHolder
import com.minux.monitoring.navigation.di.NavigationDependencies

internal object DaggerDi {

    fun initDependencyProviders(application: Application) {
        AppComponentHolder.dependencyProvider = {
            class AppDependencyHolder(
                override val block: (BaseDependencyHolder<AppDependencies>, InjectorComposeApi, NetworkApi, NavigationApi) -> AppDependencies
            ) : DependencyHolderWithThreeApi<AppDependencies, InjectorComposeApi, NetworkApi, NavigationApi>(
                firstApi = InjectorComposeComponentHolder.fetchApi(),
                secondApi = NetworkComponentHolder.fetchApi(),
                thirdApi = NavigationComponentHolder.fetchApi()
            )

            AppDependencyHolder { dependencyHolder, injectorComposeApi, networkApi, navigationApi ->
                object : AppDependencies {
                    override val activityLifecycleCallbacks: Application.ActivityLifecycleCallbacks
                        get() = injectorComposeApi.activityLifecycleCallbacks
                    override val sessionManager: SessionManager
                        get() = networkApi.sessionManager
                    override val navigationApi: NavigationApi
                        get() = navigationApi
                    override val dependencyHolder: BaseDependencyHolder<out BaseDependencies>
                        get() = dependencyHolder
                }
            }.dependencies
        }

        NavigationComponentHolder.dependencyProvider = {
            class NavigationDependencyHolder(
                override val block: (BaseDependencyHolder<NavigationDependencies>) -> NavigationDependencies
            ) : DependencyHolder<NavigationDependencies>()

            NavigationDependencyHolder { dependencyHolder ->
                object : NavigationDependencies {
                    override val dependencyHolder: BaseDependencyHolder<out BaseDependencies>
                        get() = dependencyHolder
                }
            }.dependencies
        }

        InjectorComposeComponentHolder.dependencyProvider = {
            class InjectorComposeDependencyHolder(
                override val block: (BaseDependencyHolder<InjectorComposeDependencies>) -> InjectorComposeDependencies
            ) : DependencyHolder<InjectorComposeDependencies>()

            InjectorComposeDependencyHolder { dependencyHolder ->
                object : InjectorComposeDependencies {
                    override val dependencyHolder: BaseDependencyHolder<out BaseDependencies>
                        get() = dependencyHolder
                }
            }.dependencies
        }

        NetworkComponentHolder.dependencyProvider = {
            class NetworkDependencyHolder(
                override val block: (BaseDependencyHolder<NetworkDependencies>) -> NetworkDependencies
            ) : DependencyHolder<NetworkDependencies>()

            NetworkDependencyHolder { dependencyHolder ->
                object : NetworkDependencies {
                    override val context: Application
                        get() = application
                    override val dependencyHolder: BaseDependencyHolder<out BaseDependencies>
                        get() = dependencyHolder
                }
            }.dependencies
        }

        AuthComponentHolder.dependencyProvider = {
            class AuthDependencyHolder(
                override val block: (BaseDependencyHolder<AuthDependencies>, InjectorComposeApi, NetworkApi) -> AuthDependencies
            ) : DependencyHolderWithTwoApi<AuthDependencies, InjectorComposeApi, NetworkApi>(
                firstApi = InjectorComposeComponentHolder.fetchApi(),
                secondApi = NetworkComponentHolder.fetchApi()
            )

            AuthDependencyHolder { dependencyHolder, injectorComposeApi, networkApi ->
                object : AuthDependencies {
                    override val binderBaseApi: BinderBaseApi
                        get() = injectorComposeApi.binderBaseApi
                    override val httpClient: HttpClient
                        get() = networkApi.httpClient
                    override val sessionManager: SessionManager
                        get() = networkApi.sessionManager
                    override val dependencyHolder: BaseDependencyHolder<out BaseDependencies>
                        get() = dependencyHolder
                }
            }.dependencies
        }

        CryptosComponentHolder.dependencyProvider = {
            class CryptosDependencyHolder(
                override val block: (BaseDependencyHolder<CryptosDependencies>, InjectorComposeApi, NetworkApi) -> CryptosDependencies
            ) : DependencyHolderWithTwoApi<CryptosDependencies, InjectorComposeApi, NetworkApi>(
                firstApi = InjectorComposeComponentHolder.fetchApi(),
                secondApi = NetworkComponentHolder.fetchApi()
            )

            CryptosDependencyHolder { dependencyHolder, injectorComposeApi, networkApi ->
                object : CryptosDependencies {
                    override val binderBaseApi: BinderBaseApi
                        get() = injectorComposeApi.binderBaseApi
                    override val httpClient: HttpClient
                        get() = networkApi.httpClient
                    override val dependencyHolder: BaseDependencyHolder<out BaseDependencies>
                        get() = dependencyHolder
                }
            }.dependencies
        }

        PresetsComponentHolder.dependencyProvider = {
            class PresetsDependencyHolder(
                override val block: (BaseDependencyHolder<PresetsDependencies>, InjectorComposeApi, NetworkApi) -> PresetsDependencies
            ) : DependencyHolderWithTwoApi<PresetsDependencies, InjectorComposeApi, NetworkApi>(
                firstApi = InjectorComposeComponentHolder.fetchApi(),
                secondApi = NetworkComponentHolder.fetchApi()
            )

            PresetsDependencyHolder { dependencyHolder, injectorComposeApi, networkApi ->
                object : PresetsDependencies {
                    override val binderBaseApi: BinderBaseApi
                        get() = injectorComposeApi.binderBaseApi
                    override val httpClient: HttpClient
                        get() = networkApi.httpClient
                    override val dependencyHolder: BaseDependencyHolder<out BaseDependencies>
                        get() = dependencyHolder
                }
            }.dependencies
        }
    }
}