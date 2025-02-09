package com.minux.monitoring.feature.auth.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.minux.monitoring.feature.auth.impl.di.AuthComponentHolder
import com.minux.monitoring.feature.auth.impl.presentation.ui.login.LoginRoute
import com.minux.monitoring.feature.auth.impl.presentation.ui.login.LoginViewModel
import com.minux.monitoring.feature.auth.impl.presentation.ui.register.RegisterRoute
import com.minux.monitoring.feature.auth.impl.presentation.ui.register.RegisterViewModel
import com.minux.monitoring.injector.compose.binder.BindApiToEntryLifecycle

@Composable
internal fun AuthFlowNavGraph(
    onNavigateToMainScreen: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val component = remember { AuthComponentHolder.fetchComponent() }
    val navController = rememberNavController()
    val authNavigationActions = remember(navController) {
        AuthNavigationActions(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = AuthFlowRoute.Login
    ) {
        composable<AuthFlowRoute.Login> { entry ->
            BindApiToEntryLifecycle(
                holder = AuthComponentHolder,
                navEntry = entry
            ) {
                val loginViewModel = viewModel<LoginViewModel>(factory = component.viewModelFactory)

                LoginRoute(
                    viewModel = loginViewModel,
                    onNavigate = { navController.navigate(it) },
                    onNavigateToMainScreen = onNavigateToMainScreen,
                    onShowSnackBar = onShowSnackBar
                )
            }
        }

        composable<AuthFlowRoute.Register> { entry ->
            BindApiToEntryLifecycle(
                holder = AuthComponentHolder,
                navEntry = entry
            ) {
                val registerViewModel =
                    viewModel<RegisterViewModel>(factory = component.viewModelFactory)

                RegisterRoute(
                    viewModel = registerViewModel,
                    onNavigate = {
                        if (it is AuthFlowRoute.Login)
                            authNavigationActions.navigateToLogin()
                        else
                            navController.navigate(it)
                    },
                    onNavigateToMainScreen = onNavigateToMainScreen,
                    onShowSnackBar = onShowSnackBar
                )
            }
        }
    }
}