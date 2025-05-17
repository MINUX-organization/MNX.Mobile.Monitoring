package com.minux.monitoring.ui.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.minux.monitoring.app.MinuxApp
import com.minux.monitoring.core.designsystem.theme.MNXTheme

class AppActivity : ComponentActivity() {

    private val appViewModel by viewModels<AppViewModel>(
        factoryProducer = { (application as MinuxApp).appComponent.viewModelFactory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition {
            appViewModel.appUiState.value.isAuthorized == null
        }

        splashScreen.setOnExitAnimationListener {
            it.remove()
        }

        setContent {
            val state by appViewModel.appUiState.collectAsStateWithLifecycle()

            MNXTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MNXApp(appUiState = state)
                }
            }
        }
    }
}